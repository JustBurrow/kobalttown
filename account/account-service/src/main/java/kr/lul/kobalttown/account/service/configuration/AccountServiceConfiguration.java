package kr.lul.kobalttown.account.service.configuration;

import kr.lul.kobalttown.account.service.properties.AccountServiceProperties;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2019/12/18
 */
public class AccountServiceConfiguration {
  private ActivateCodeConfiguration activateCode;

  public AccountServiceConfiguration(final AccountServiceProperties properties) {
    notNull(properties, "properties");

    this.activateCode = new ActivateCodeConfiguration(properties.getActivateCode());
  }

  public ActivateCodeConfiguration getActivateCode() {
    return this.activateCode;
  }

  @Override
  public String toString() {
    return format("{activateCode=%s}", this.activateCode);
  }
}
