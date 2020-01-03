package kr.lul.kobalttown.account.service;

import kr.lul.common.data.Context;
import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.data.dao.AccountDao;
import kr.lul.kobalttown.account.data.dao.CredentialDao;
import kr.lul.kobalttown.account.data.dao.ValidationCodeDao;
import kr.lul.kobalttown.account.data.factory.AccountFactory;
import kr.lul.kobalttown.account.data.factory.CredentialFactory;
import kr.lul.kobalttown.account.data.factory.ValidationCodeFactory;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;
import kr.lul.kobalttown.account.domain.ValidationCode;
import kr.lul.kobalttown.account.service.configuration.ActivateCodeConfiguration;
import kr.lul.kobalttown.account.service.configuration.WelcomeConfiguration;
import kr.lul.kobalttown.account.service.params.CreateAccountParams;
import kr.lul.kobalttown.account.service.params.ReadAccountParams;
import kr.lul.support.spring.mail.MailConfiguration;
import kr.lul.support.spring.mail.MailParams;
import kr.lul.support.spring.mail.MailResult;
import kr.lul.support.spring.mail.MailService;
import kr.lul.support.spring.security.crypto.SecurityEncoder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.lang.String.format;
import static java.util.Map.entry;
import static java.util.Map.ofEntries;
import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.kobalttown.account.domain.ValidationCodeUtil.code;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@Service
class AccountServiceImpl implements AccountService {
  private static final Logger log = getLogger(AccountServiceImpl.class);

  @Autowired
  private WelcomeConfiguration welcome;
  @Autowired
  private ActivateCodeConfiguration activateCode;

  @Autowired
  private AccountFactory factory;
  @Autowired
  private AccountDao dao;
  @Autowired
  private CredentialFactory credentialFactory;
  @Autowired
  private CredentialDao credentialDao;
  @Autowired
  private ValidationCodeFactory validationCodeFactory;
  @Autowired
  private ValidationCodeDao validationCodeDao;
  @Autowired
  private SecurityEncoder securityEncoder;
  @Autowired
  private MailService mailService;
  @Autowired
  private TimeProvider timeProvider;

  @PostConstruct
  private void postConstruct() {
    log.info("#postConstruct welcome={}", this.welcome);
    log.info("#postConstruct activateCode={}", this.activateCode);
  }

  private Future<MailResult> sendWelcome(final CreateAccountParams rootParams) {
    final MailConfiguration mailConfig = this.welcome.getMail();
    if (log.isDebugEnabled())
      log.debug("#sendWelcome mailConfig={}", mailConfig);

    // TODO userKey 추가.
    final Map<String, Object> model = ofEntries(
        entry("nickname", rootParams.getNickname()),
        entry("email", rootParams.getEmail()));
    if (log.isDebugEnabled())
      log.debug("#sendWelcome (context={}) model={}", rootParams.getContext(), model);

    final MailParams params = new MailParams(rootParams.getContext(), mailConfig.getFrom(), rootParams.getEmail(),
        mailConfig.getTitle(), mailConfig.getTemplate(), true,
        model);
    if (log.isDebugEnabled())
      log.debug("#sendWelcome (context={}) params={}", rootParams.getContext(), params);

    final Future<MailResult> future = this.mailService.asyncSend(params);
    if (log.isDebugEnabled())
      log.debug("#sendWelcome (context={}) future={}", rootParams.getContext(), future);
    else if (log.isInfoEnabled())
      log.info("#sendWelcome (context={}) welcome mail ready to send : to={}", rootParams.getContext(),
          rootParams.getEmail());

    if (log.isTraceEnabled())
      log.trace("#sendWelcome (context={}) return : {}", rootParams.getContext(), future);
    return future;
  }

  private Future<MailResult> sendActivateCode(final CreateAccountParams rootParams,
      final ValidationCode validationCode) {
    final MailConfiguration mailConfig = this.activateCode.getMail();
    if (log.isDebugEnabled())
      log.debug("#sendActivateCode (context={}) mailConfig={}", rootParams.getContext(), mailConfig);

    final Map<String, Object> model = ofEntries(
        entry("domain", this.activateCode.getDomain()),
        entry("code", validationCode.getCode()),
        entry("expireAt", this.timeProvider.zonedDateTime(validationCode.getExpireAt())));
    if (log.isDebugEnabled())
      log.debug("#sendActivateCode (context={}) model={}", rootParams.getContext(), model);

    final MailParams params = new MailParams(rootParams.getContext(),
        mailConfig.getFrom(), rootParams.getEmail(),
        mailConfig.getTitle(), mailConfig.getTemplate(), true,
        model);
    if (log.isDebugEnabled())
      log.debug("#sendActivateCode (context={}) params={}", rootParams.getContext(), params);

    final Future<MailResult> future = this.mailService.asyncSend(params);
    if (log.isDebugEnabled())
      log.debug("#sendActivateCode (context={}) future={}", rootParams.getContext(), future);
    else if (log.isInfoEnabled())
      log.info("#sendActivateCode (context={}) activate code ready to send : to={}", rootParams.getContext(), future);

    if (log.isTraceEnabled())
      log.trace("#sendActivateCode (context={}) return : {}", rootParams.getContext(), future);
    return future;
  }

  private void handleAsyncTasks(final Context context, final List<Future> asyncTasks) {
    for (final Future task : asyncTasks) {
      if (log.isDebugEnabled())
        log.debug("#handleAsyncTasks (context={}) task={}", context, task);

      try {
        final Object rv = task.get();
        if (log.isDebugEnabled())
          log.debug("#handleAsyncTasks (context={}) task complete : rv={}", context, rv);
      } catch (final InterruptedException | ExecutionException e) {
        // TODO 에러 처리.
        final String msg = "fail to wait async task : task=" + task;
        log.warn(format("#handleAsyncTasks (context=%s) %s", context, msg), e);
        throw new RuntimeException(msg, e);
      }
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

    Account account;
    Credential credential;

    // 계정 정보 등록.
    account = this.factory.create(
        params.getContext(), params.getNickname(), !this.activateCode.isEnable(), params.getTimestamp());
    account = this.dao.create(params.getContext(), account);
    if (log.isDebugEnabled())
      log.debug("#create (context={}) account={}", params.getContext(), account);

    // 인증 정보 등록.
    credential = this.credentialFactory.create(params.getContext(), account, params.getUserKey(),
        this.securityEncoder.encode(params.getPassword()), params.getTimestamp());
    credential = this.credentialDao.create(params.getContext(), credential);
    if (log.isDebugEnabled())
      log.debug("#create (context={}) nickname credential : {}", params.getContext(), credential);

    credential = this.credentialFactory.create(params.getContext(), account, params.getEmail(),
        this.securityEncoder.encode(params.getPassword()), params.getTimestamp());
    credential = this.credentialDao.create(params.getContext(), credential);
    if (log.isDebugEnabled())
      log.debug("#create (context={}) email credential : {}", params.getContext(), credential);

    final List<Future> asyncTasks = new ArrayList<>();
    // TODO 안내 메일 관련 코드를 해당 메서드로 이동.
    // 신규 계정 정보 알림.
    if (this.welcome.isEnable()) {
      if (this.welcome.isWait())
        asyncTasks.add(sendWelcome(params));
      else
        sendWelcome(params);
    } else if (log.isInfoEnabled()) {
      log.info("#create (context={}) welcome disabled. do not send welcome email : nickname={}, email={}",
          params.getContext(), params.getNickname(), params.getEmail());
    }

    if (this.activateCode.isEnable()) {
      String code;
      do {
        code = code();
      } while (this.validationCodeDao.exists(params.getContext(), code));
      final ValidationCode validationCode = this.validationCodeDao.create(params.getContext(),
          this.validationCodeFactory.create(params.getContext(), account, code, params.getTimestamp()));
      if (this.activateCode.isWait())
        asyncTasks.add(sendActivateCode(params, validationCode));
      else
        sendActivateCode(params, validationCode);
    } else if (log.isInfoEnabled()) {
      log.info("#create (context={}) activate code disabled. do not send validation email : nickname={}, email={}",
          params.getContext(), params.getNickname(), params.getEmail());
    }

    if (!asyncTasks.isEmpty())
      handleAsyncTasks(params.getContext(), asyncTasks);

    if (log.isTraceEnabled())
      log.trace("#create (context={}) return : {}", params.getContext(), account);
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
}
