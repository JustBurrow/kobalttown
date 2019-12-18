package kr.lul.kobalttown.account.service;

import kr.lul.kobalttown.account.data.dao.AccountDao;
import kr.lul.kobalttown.account.data.dao.CredentialDao;
import kr.lul.kobalttown.account.data.factory.AccountFactory;
import kr.lul.kobalttown.account.data.factory.CredentialFactory;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;
import kr.lul.kobalttown.account.service.configuration.ActivationConfiguration;
import kr.lul.kobalttown.account.service.params.CreateAccountParams;
import kr.lul.kobalttown.account.service.params.ReadAccountParams;
import kr.lul.support.spring.security.crypto.SecurityEncoder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

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
  private JavaMailSender javaMailSender;
  @Autowired
  private MailService mailService;
  @Autowired
  private ActivationConfiguration activation;


  @PostConstruct
  private void postConstruct() {
    requireNonNull(this.accountFactory, "accountFactory is not autowired.");
    requireNonNull(this.credentialFactory, "credentialFactory is not autowired");
    requireNonNull(this.accountDao, "accountDao is not autowired.");
    requireNonNull(this.credentialDao, "credentialDao is not autowired.");
    requireNonNull(this.securityEncoder, "securityEncoder is not autowired.");
    requireNonNull(this.javaMailSender, "javaMailSender is not autowired.");
    requireNonNull(this.mailService, "mailService is not autowired.");
    requireNonNull(this.activation, "activationConfiguration is not autowired.");

    log.info("{} is ready.", AccountServiceImpl.class.getCanonicalName());
  }

  @Override
  public Account create(final CreateAccountParams params) {
    if (log.isTraceEnabled())
      log.trace("#create args : params={}", params);
    notNull(params, "params");

    Account account = this.accountFactory.create(params.getContext(), params.getNickname(), params.getTimestamp());
    account = this.accountDao.create(params.getContext(), account);

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

    this.mailService.send(
        params.getContext(), this.activation.getFrom(), params.getEmail(),
        this.activation.getTitle(), this.activation.getTemplate(),
        Map.of("domain", this.activation.getDomain(), "code", "some_code"));

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
