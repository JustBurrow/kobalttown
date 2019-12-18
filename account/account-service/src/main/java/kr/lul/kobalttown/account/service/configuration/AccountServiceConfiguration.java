package kr.lul.kobalttown.account.service.configuration;

import kr.lul.kobalttown.account.service.properties.AccountServiceProperties;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2019/12/18
 */
public class AccountServiceConfiguration {
  private ActivationConfiguration activation;

  public AccountServiceConfiguration(final AccountServiceProperties properties) {
    notNull(properties, "properties");

    this.activation = new ActivationConfiguration(properties.getActivation());
  }

  public ActivationConfiguration getActivation() {
    return this.activation;
  }

  @Override
  public String toString() {
    return format("{activation=%s}", this.activation);
  }
}
