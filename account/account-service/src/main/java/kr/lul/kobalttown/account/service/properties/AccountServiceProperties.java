package kr.lul.kobalttown.account.service.properties;

import org.springframework.beans.factory.annotation.Configurable;

/**
 * @author justburrow
 * @since 2019/12/18
 */
@Configurable
public class AccountServiceProperties {
  private ActivateCodeProperties activateCode;

  public ActivateCodeProperties getActivateCode() {
    return this.activateCode;
  }

  public void setActivateCode(final ActivateCodeProperties activateCode) {
    this.activateCode = activateCode;
  }

  @Override
  public String toString() {
    return new StringBuilder()
        .append("{activateCode=").append(this.activateCode)
        .append('}').toString();
  }
}
