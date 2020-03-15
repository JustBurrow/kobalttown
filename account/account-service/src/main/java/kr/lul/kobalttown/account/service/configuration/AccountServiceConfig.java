package kr.lul.kobalttown.account.service.configuration;

import kr.lul.kobalttown.account.service.properties.AccountServiceProperties;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2019/12/18
 */
public class AccountServiceConfig {
  private WelcomeConfig welcome;
  private EnableCodeConfig validationCode;

  public AccountServiceConfig(final AccountServiceProperties properties) {
    notNull(properties, "properties");
    this.welcome = new WelcomeConfig(properties.getWelcome());
    this.validationCode = new EnableCodeConfig(properties.getValidationCode());
  }

  public WelcomeConfig getWelcome() {
    return this.welcome;
  }

  public EnableCodeConfig getValidationCode() {
    return this.validationCode;
  }

  @Override
  public String toString() {
    return format("{welcome=%s, validationCode=%s}", this.welcome, this.validationCode);
  }
}
