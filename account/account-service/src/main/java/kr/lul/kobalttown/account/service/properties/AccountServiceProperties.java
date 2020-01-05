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
  private ValidationCodeProperties validationCode;

  public WelcomeProperties getWelcome() {
    return this.welcome;
  }

  public void setWelcome(final WelcomeProperties welcome) {
    this.welcome = welcome;
  }

  public ValidationCodeProperties getValidationCode() {
    return this.validationCode;
  }

  public void setValidationCode(final ValidationCodeProperties validationCode) {
    this.validationCode = validationCode;
  }

  @Override
  public String toString() {
    return new StringBuilder('{')
               .append("welcome=").append(this.welcome)
               .append(", validationCode=").append(this.validationCode)
               .append('}').toString();
  }
}
