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
  private WelcomeProperties welcome;
  @NotNull
  private ActivateCodeProperties activateCode;

  public WelcomeProperties getWelcome() {
    return this.welcome;
  }

  public void setWelcome(final WelcomeProperties welcome) {
    this.welcome = welcome;
  }

  public ActivateCodeProperties getActivateCode() {
    return this.activateCode;
  }

  public void setActivateCode(final ActivateCodeProperties activateCode) {
    this.activateCode = activateCode;
  }

  @Override
  public String toString() {
    return new StringBuilder('{')
        .append("welcome=").append(this.welcome)
        .append(", activateCode=").append(this.activateCode)
        .append('}').toString();
  }
}
