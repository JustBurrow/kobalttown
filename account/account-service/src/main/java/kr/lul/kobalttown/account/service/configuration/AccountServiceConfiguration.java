package kr.lul.kobalttown.account.service.configuration;

import kr.lul.kobalttown.account.service.properties.AccountServiceProperties;
import kr.lul.support.spring.mail.MailConfiguration;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2019/12/18
 */
public class AccountServiceConfiguration {
  private MailConfiguration welcomeMail;
  private ActivateCodeConfiguration activateCode;

  public AccountServiceConfiguration(final AccountServiceProperties properties) {
    notNull(properties, "properties");
    this.welcomeMail = new MailConfiguration(properties.getWelcome());
    this.activateCode = new ActivateCodeConfiguration(properties.getActivateCode());
  }

  public MailConfiguration getWelcomeMail() {
    return this.welcomeMail;
  }

  public ActivateCodeConfiguration getActivateCode() {
    return this.activateCode;
  }

  @Override
  public String toString() {
    return format("{welcomeMail=%s, activateCode=%s}", this.welcomeMail, this.activateCode);
  }
}
