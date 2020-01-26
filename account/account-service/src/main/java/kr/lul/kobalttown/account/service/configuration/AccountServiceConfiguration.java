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
  private EnableCodeConfiguration validationCode;

  public AccountServiceConfiguration(final AccountServiceProperties properties) {
    notNull(properties, "properties");
    this.welcome = new WelcomeConfiguration(properties.getWelcome());
    this.validationCode = new EnableCodeConfiguration(properties.getValidationCode());
  }

  public WelcomeConfiguration getWelcome() {
    return this.welcome;
  }

  public EnableCodeConfiguration getValidationCode() {
    return this.validationCode;
  }

  @Override
  public String toString() {
    return format("{welcome=%s, validationCode=%s}", this.welcome, this.validationCode);
  }
}
