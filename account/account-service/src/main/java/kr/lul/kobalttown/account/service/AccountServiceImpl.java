package kr.lul.kobalttown.account.service;

import kr.lul.kobalttown.account.data.dao.AccountDao;
import kr.lul.kobalttown.account.data.dao.CredentialDao;
import kr.lul.kobalttown.account.data.factory.AccountFactory;
import kr.lul.kobalttown.account.data.factory.CredentialFactory;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;
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
import java.util.Map;
import java.util.concurrent.Future;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;
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
  private WelcomeConfiguration welcome;
  @Autowired
  private ActivateCodeConfiguration activateCode;

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


  @PostConstruct
  private void postConstruct() {
    log.info("#postConstruct welcome={}", this.welcome);
    log.info("#postConstruct activateCode={}", this.activateCode);
  }

  private void sendWelcome(final CreateAccountParams rootParams) {
    final MailConfiguration mailConfig = this.welcome.getMail();
    if (log.isDebugEnabled())
      log.debug("#sendWelcome mailConfig={}", mailConfig);

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
  }

  private void sendActivateCode(final CreateAccountParams rootParams) {
    final MailConfiguration mailConfig = this.activateCode.getMail();
    if (log.isDebugEnabled())
      log.debug("#sendActivateCode (context={}) mailConfig={}", rootParams.getContext(), mailConfig);

    final Map<String, Object> model = ofEntries(
        entry("domain", this.activateCode.getDomain()),
        entry("code", "some_code"));
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
  }

  @Override
  public Account create(final CreateAccountParams params) {
    if (log.isTraceEnabled())
      log.trace("#create args : params={}", params);
    notNull(params, "params");

    Account account;
    Credential credential;

    // 계정 정보 등록.
    account = this.accountFactory.create(
        params.getContext(), params.getNickname(), !this.activateCode.isEnable(), params.getTimestamp());
    account = this.accountDao.create(params.getContext(), account);
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

    // 신규 계정 정보 알림.
    if (this.welcome.isEnable()) {
      sendWelcome(params);
    } else if (log.isInfoEnabled()) {
      log.info("#create (context={}) welcome disabled. do not send welcome email : nickname={}, email={}",
          params.getContext(), params.getNickname(), params.getEmail());
    }

    if (this.activateCode.isEnable()) {
      sendActivateCode(params);
    } else if (log.isInfoEnabled()) {
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
