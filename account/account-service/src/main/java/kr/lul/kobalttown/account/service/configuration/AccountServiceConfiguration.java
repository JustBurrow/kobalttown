package kr.lul.kobalttown.account.service.configuration;

import kr.lul.kobalttown.account.service.properties.AccountServiceProperties;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2019/12/18
 */
public class AccountServiceConfiguration {
  private WelcomeConfiguration welcome;
  private ActivateCodeConfiguration activateCode;

  public AccountServiceConfiguration(final AccountServiceProperties properties) {
    notNull(properties, "properties");
    this.welcome = new WelcomeConfiguration(properties.getWelcome());
    this.activateCode = new ActivateCodeConfiguration(properties.getActivateCode());
  }

  public WelcomeConfiguration getWelcome() {
    return this.welcome;
  }

  public ActivateCodeConfiguration getActivateCode() {
    return this.activateCode;
  }

  @Override
  public String toString() {
    return format("{welcome=%s, activateCode=%s}", this.welcome, this.activateCode);
  }
}
