package kr.lul.kobalttown.account.service.properties;

import org.springframework.beans.factory.annotation.Configurable;

/**
 * @author justburrow
 * @since 2019/12/18
 */
@Configurable
public class AccountServiceProperties {
  private ActivationProperties activation;

  public ActivationProperties getActivation() {
    return this.activation;
  }

  public void setActivation(final ActivationProperties activation) {
    this.activation = activation;
  }

  @Override
  public String toString() {
    return new StringBuilder()
        .append("{activation=").append(this.activation)
        .append('}').toString();
  }
}
