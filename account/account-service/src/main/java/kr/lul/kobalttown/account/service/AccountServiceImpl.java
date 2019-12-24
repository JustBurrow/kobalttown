package kr.lul.kobalttown.account.service;

import kr.lul.kobalttown.account.data.dao.AccountDao;
import kr.lul.kobalttown.account.data.dao.CredentialDao;
import kr.lul.kobalttown.account.data.factory.AccountFactory;
import kr.lul.kobalttown.account.data.factory.CredentialFactory;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;
import kr.lul.kobalttown.account.service.configuration.ActivateCodeConfiguration;
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
import java.util.concurrent.Future;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;
import static java.util.Objects.requireNonNull;
import static kr.lul.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@Service
class AccountServiceImpl implements AccountService {
  private static final Logger log = getLogger(AccountServiceImpl.class);

  @Autowired
  private AccountFactory accountFactory;
  @Autowired
  private CredentialFactory credentialFactory;
  @Autowired
  private AccountDao accountDao;
  @Autowired
  private CredentialDao credentialDao;
  @Autowired
  private SecurityEncoder securityEncoder;
  @Autowired
  private MailService mailService;
  @Autowired
  private ActivateCodeConfiguration activateCode;


  @PostConstruct
  private void postConstruct() {
    requireNonNull(this.accountFactory, "accountFactory is not autowired.");
    requireNonNull(this.credentialFactory, "credentialFactory is not autowired");
    requireNonNull(this.accountDao, "accountDao is not autowired.");
    requireNonNull(this.credentialDao, "credentialDao is not autowired.");
    requireNonNull(this.securityEncoder, "securityEncoder is not autowired.");
    requireNonNull(this.mailService, "mailService is not autowired.");
    requireNonNull(this.activateCode, "activateCode is not autowired.");

    log.info(
        "#postConstruct accountFactory={}, credentialFactory={}, accountDao={}, credentialDao={}, securityEncoder={}, mailService={}, activateCode={}",
        this.accountFactory, this.credentialFactory, this.accountDao, this.credentialDao, this.securityEncoder,
        this.mailService, this.activateCode);
  }

  @Override
  public Account create(final CreateAccountParams params) {
    if (log.isTraceEnabled())
      log.trace("#create args : params={}", params);
    notNull(params, "params");

    // TODO 메일로 계정 인증하기 기능이 활성화 됐는지에 따라 초기 enabled 값을 변경.
    // 계정 정보 등록.
    Account account = this.accountFactory
        .create(params.getContext(), params.getNickname(), !this.activateCode.isEnable(), params.getTimestamp());
    account = this.accountDao.create(params.getContext(), account);

    // 인증 정보 등록.
    Credential credential = this.credentialFactory.create(params.getContext(), account, params.getNickname(),
        this.securityEncoder.encode(params.getPassword()), params.getTimestamp());
    credential = this.credentialDao.create(params.getContext(), credential);
    if (log.isTraceEnabled())
      log.trace("#create (context={}) nickname credential : {}", params.getContext(), credential);

    credential = this.credentialFactory.create(params.getContext(), account, params.getEmail(),
        this.securityEncoder.encode(params.getPassword()), params.getTimestamp());
    credential = this.credentialDao.create(params.getContext(), credential);
    if (log.isTraceEnabled())
      log.trace("#create (context={}) email credential : {}", params.getContext(), credential);

    if (this.activateCode.isEnable()) {
      final MailConfiguration mailConfig = this.activateCode.getMail();
      if (log.isDebugEnabled())
        log.debug("#create (context={}) mailConfig={}", params.getContext(), mailConfig);

      final MailParams mailParams = new MailParams(params.getContext(),
          mailConfig.getFrom(), params.getEmail(),
          mailConfig.getTitle(), mailConfig.getTemplate(), true,
          ofEntries(entry("domain", this.activateCode.getDomain()), entry("code", "some_code")));
      if (log.isDebugEnabled())
        log.debug("#create (context={}) mailParams={}", params.getContext(), mailParams);

      final Future<MailResult> future = this.mailService.asyncSend(mailParams);
      if (log.isDebugEnabled())
        log.debug("#create (context={}) future={}", params.getContext(), future);
    } else {
      if (log.isInfoEnabled())
        log.info("#create (context={}) activate code disabled. do not send validation email : nickname={}, email={}",
            params.getContext(), params.getNickname(), params.getEmail());
    }

    if (log.isTraceEnabled())
      log.trace("#create (context={}) return : {}", params.getContext(), account);
    return account;
  }

  @Override
  public Account read(final ReadAccountParams params) {
    if (log.isTraceEnabled())
      log.trace("#read args : params={}", params);
    notNull(params, "params");

    final Account account = this.accountDao.read(params.getContext(), params.getId());

    if (log.isTraceEnabled())
      log.trace("#read (context={}) return : {}", params.getContext(), account);
    return account;
  }
}
