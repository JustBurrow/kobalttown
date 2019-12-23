package kr.lul.kobalttown.account.service.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author justburrow
 * @since 2019/12/18
 */
@Configuration
@ConfigurationProperties("kr.lul.kobalttown.account.service")
@Validated
public class AccountServiceProperties {
  @NotNull
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
