package kr.lul.kobalttown.account.service;

import kr.lul.common.data.Context;
import kr.lul.common.util.DisabledPropertyException;
import kr.lul.common.util.TimeProvider;
import kr.lul.common.util.ValidationException;
import kr.lul.kobalttown.account.data.dao.AccountDao;
import kr.lul.kobalttown.account.data.dao.CredentialDao;
import kr.lul.kobalttown.account.data.dao.EnableCodeDao;
import kr.lul.kobalttown.account.data.factory.AccountFactory;
import kr.lul.kobalttown.account.data.factory.CredentialFactory;
import kr.lul.kobalttown.account.data.factory.EnableCodeFactory;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;
import kr.lul.kobalttown.account.domain.EnableCode;
import kr.lul.kobalttown.account.service.configuration.EnableCodeConfig;
import kr.lul.kobalttown.account.service.configuration.WelcomeConfig;
import kr.lul.kobalttown.account.service.params.*;
import kr.lul.support.spring.mail.MailConfiguration;
import kr.lul.support.spring.mail.MailParams;
import kr.lul.support.spring.mail.MailResult;
import kr.lul.support.spring.mail.MailService;
import kr.lul.support.spring.security.crypto.SecurityEncoder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.lang.String.format;
import static java.util.Map.entry;
import static java.util.Map.ofEntries;
import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.kobalttown.account.domain.Credential.ATTR_ACCOUNT;
import static kr.lul.kobalttown.account.domain.EnableCodeUtil.token;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@Service
class AccountServiceImpl implements AccountService {
  private static final Logger log = getLogger(AccountServiceImpl.class);

  @Autowired
  private WelcomeConfig welcome;
  @Autowired
  private EnableCodeConfig enableCode;

  @Autowired
  private AccountFactory factory;
  @Autowired
  private AccountDao dao;
  @Autowired
  private CredentialFactory credentialFactory;
  @Autowired
  private CredentialDao credentialDao;
  @Autowired
  private EnableCodeFactory enableCodeFactory;
  @Autowired
  private EnableCodeDao enableCodeDao;
  @Autowired
  private SecurityEncoder securityEncoder;
  @Autowired
  private MailService mailService;
  @Autowired
  private TimeProvider timeProvider;

  @PostConstruct
  private void postConstruct() {
    log.info("#postConstruct welcome={}", this.welcome);
    log.info("#postConstruct enableCode={}", this.enableCode);
  }

  private Credential createCredential(final Context context, final Account account, final String publicKey, final String password,
      final Instant createdAt) {
    if (log.isTraceEnabled())
      log.trace("#createCredential args : context={}, account={}, publicKey={}, password={}, createdAt={}",
          context, account, publicKey, password, createdAt);

    Credential credential =
        this.credentialFactory.create(context, account, publicKey, this.securityEncoder.encode(password), createdAt);
    credential = this.credentialDao.create(context, credential);

    if (log.isTraceEnabled())
      log.trace("#createCredential (context={}) return : {}", context, credential);
    return credential;
  }

  /**
   * 가입 환연 메일 발송.
   *
   * @param context 컨텍스트.
   * @param account 신규 계정.
   * @param email   이메일.
   * @param userKey 로그인용 유저 키.
   *
   * @return 비동기 발송일 때는 {@link Future<MailResult>}, 동기 발송일 경우에는 {@code null}.
   */
  private Future<MailResult> sendWelcome(final Context context, final Account account, final String email, final String userKey) {
    if (log.isTraceEnabled())
      log.trace("#sendWelcome args : context={}, account={}, email={}, userKey={}", context, account, email, userKey);

    // 설정.
    final MailConfiguration mailConfig = this.welcome.getMail();
    if (log.isDebugEnabled())
      log.debug("#sendWelcome mailConfig={}", mailConfig);

    // 메일 변수 설정.
    final Map<String, Object> model = ofEntries(
        entry("nickname", account.getNickname()),
        entry("email", email),
        entry("userKey", userKey));
    if (log.isDebugEnabled())
      log.debug("#sendWelcome (context={}) model={}", context, model);

    // 메일 발송.
    final MailParams params = new MailParams(context, mailConfig.getFrom(), email,
        mailConfig.getTitle(), mailConfig.getTemplate(), true, model);
    if (this.welcome.isAsync()) {
      final Future<MailResult> result = this.mailService.asyncSend(params);
      if (log.isTraceEnabled())
        log.trace("#sendWelcome (context={}) return : {}", context, result);
      return result;
    } else {
      final MailResult result = this.mailService.send(params);
      if (log.isTraceEnabled())
        log.trace("#sendWelcome (context={}) result : result={}", context, result);
      return null;
    }
  }

  private EnableCode createEnableCode(final Context context, final Account account, final String email,
      final Instant createdAt) {
    if (log.isTraceEnabled())
      log.trace("#createEnableCode args : context={}, account={}, email={}, createdAt={}",
          context, account, email, createdAt);

    String token;
    do {
      token = token();
    } while (this.enableCodeDao.exists(context, token));

    EnableCode code = this.enableCodeFactory.create(context, account, email, token, createdAt);
    code = this.enableCodeDao.create(context, code);

    if (log.isTraceEnabled())
      log.trace("#createEnableCode (context={}) return : {}", context, code);
    return code;
  }

  private Future<MailResult> sendEnableCode(final Context context, final EnableCode code) {
    if (log.isTraceEnabled())
      log.trace("#sendEnableCode args : context={}, code={}", context, code);

    final MailConfiguration mailConfig = this.enableCode.getMail();
    if (log.isDebugEnabled())
      log.debug("#sendEnableCode (context={}) mailConfig={}", context, mailConfig);

    // 검증 코드 발행.

    // 메일 내용 설정.
    final Map<String, Object> model = ofEntries(
        entry("domain", this.enableCode.getDomain()),
        entry("code", code.getToken()),
        entry("expireAt", this.timeProvider.zonedDateTime(code.getExpireAt())));
    if (log.isDebugEnabled())
      log.debug("#sendEnableCode (context={}) model={}", context, model);

    // 검증 코드 메일 전송.
    final MailParams params = new MailParams(context, mailConfig.getFrom(), code.getEmail(),
        mailConfig.getTitle(), mailConfig.getTemplate(), true, model);
    if (this.enableCode.isAsync()) {
      final Future<MailResult> result = this.mailService.asyncSend(params);
      if (log.isTraceEnabled())
        log.trace("#sendEnableCode (context={}) return : {}", context, result);
      return result;
    } else {
      final MailResult result = this.mailService.send(params);
      if (log.isTraceEnabled())
        log.trace("#sendEnableCode (context={}) result : result={}", context, result);
      return null;
    }
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.account.service.AccountService
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Account create(final CreateAccountParams params) {
    if (log.isTraceEnabled())
      log.trace("#create args : params={}", params);
    notNull(params, "params");

    if (this.dao.existsNickname(params.getContext(), params.getNickname()))
      throw new ValidationException(Account.ATTR_NICKNAME, params.getNickname(),
          "이미 사용중인 별명입니다 : " + params.getNickname());
    if (this.credentialDao.existsPublicKey(params.getContext(), params.getEmail()))
      throw new ValidationException(Credential.ATTR_EMAIL, params.getEmail(), "이미 사용중인 이메일입니다 : " + params.getEmail());

    // 계정 정보 등록.
    Account account = this.factory.create(
        params.getContext(), params.getNickname(), !this.enableCode.isEnable(), params.getTimestamp());
    account = this.dao.create(params.getContext(), account);
    if (log.isDebugEnabled())
      log.debug("#create (context={}) account={}", params.getContext(), account);

    // 인증 정보 등록.
    final List<Credential> credentials = List.of(
        createCredential(params.getContext(), account, params.getUserKey(), params.getPassword(), params.getTimestamp()),
        createCredential(params.getContext(), account, params.getEmail(), params.getPassword(), params.getTimestamp()));
    if (log.isDebugEnabled())
      log.debug("#create (context={}) credentials={}", params.getContext(), credentials);

    final List<Future<MailResult>> tasks = new ArrayList<>();
    // 신규 계정 정보 알림.
    if (this.welcome.isEnable())
      tasks.add(sendWelcome(params.getContext(), account, params.getEmail(), params.getUserKey()));

    // 계정 활성화 코드 발행 및 전송.
    if (this.enableCode.isEnable()) {
      final EnableCode code = createEnableCode(params.getContext(), account, params.getEmail(), params.getTimestamp());
      if (log.isDebugEnabled())
        log.debug("#create (context={}) code={}", params.getContext(), code);
      tasks.add(sendEnableCode(params.getContext(), code));
    }

    // 비동기 작업 결과 처리.
    tasks.stream().filter(Objects::nonNull).forEach(task -> {
      try {
        final MailResult result = task.get();
        if (log.isInfoEnabled())
          log.info("#create (context={}) result={}", params.getContext(), result);
      } catch (final InterruptedException | ExecutionException e) {
        log.warn(format("#create (context={}) fail to complete async task : task" + task, e));
      }
    });

    if (log.isTraceEnabled())
      log.trace("#create (context={}) return : {}", params.getContext(), account);
    return account;
  }

  @Override
  public Account enable(final EnableAccountParams params) {
    if (log.isTraceEnabled())
      log.trace("#enable args : params={}", params);

    if (!this.enableCode.isEnable())
      throw new DisabledPropertyException("validationCode.enabled");

    final EnableCode code = this.enableCodeDao.read(params.getContext(), params.getToken());
    if (log.isDebugEnabled())
      log.debug("#enable (context={}) code={}", params.getContext(), code);

    code.use(params.getTimestamp());

    final Account account = code.getAccount();
    if (log.isTraceEnabled())
      log.trace("#enable (context={}) return : {}", params.getContext(), account);
    return account;
  }

  @Override
  public Account read(final ReadAccountParams params) {
    if (log.isTraceEnabled())
      log.trace("#read args : params={}", params);
    notNull(params, "params");

    final Account account = this.dao.read(params.getContext(), params.getId());

    if (log.isTraceEnabled())
      log.trace("#read (context={}) return : {}", params.getContext(), account);
    return account;
  }

  @Override
  public EnableCode issue(final IssueEnableCodeParams params) {
    if (log.isTraceEnabled())
      log.trace("#issue args : params={}", params);

    final List<EnableCode> codes = this.enableCodeDao.list(params.getContext(), params.getEmail());
    if (codes.isEmpty())
      throw new ValidationException(EnableCode.ATTR_EMAIL, params.getEmail(), "no data : email=" + params.getEmail());

    codes.forEach(vc -> vc.inactive(params.getTimestamp()));
    final EnableCode code = createEnableCode(params.getContext(), codes.get(0).getAccount(), params.getEmail(),
        params.getTimestamp());
    sendEnableCode(params.getContext(), code);

    if (log.isTraceEnabled())
      log.trace("#issue (context={}) return : {}", params.getContext(), code);
    return code;
  }

  @Override
  public void update(final UpdatePasswordParams params) {
    if (log.isTraceEnabled())
      log.trace("#update args : params={}", params);
    notNull(params, "params");

    final List<Credential> credentials = this.credentialDao.read(params.getContext(), params.getUser());
    if (credentials.isEmpty())
      throw new ValidationException(ATTR_ACCOUNT, params.getUser(), "has no credential.");

    // validation
    for (final Credential credential : credentials) {
      if (!this.securityEncoder.matches(params.getCurrent(), credential.getSecretHash())) {
        throw new ValidationException("current", null, "current password does not match.");
      }
    }

    // update password.
    credentials.stream()
        .map(credential -> {
          final Credential newCredential =
              this.credentialFactory.create(params.getContext(), params.getUser(), credential.getPublicKey(),
                  this.securityEncoder.encode(params.getPassword()), params.getTimestamp());
          log.info("#update newCredential={}", newCredential);
          this.credentialDao.delete(params.getContext(), credential);
          return newCredential;
        })
        .forEach(credential -> this.credentialDao.create(params.getContext(), credential));
  }
}
