package kr.lul.kobalttown.account.service.configuration;

import kr.lul.kobalttown.account.service.properties.ValidationCodeProperties;
import kr.lul.support.spring.mail.MailConfiguration;

import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2019/12/18
 */
public class ValidationCodeConfiguration {
  private boolean enable;
  private boolean async;
  private String domain;
  private MailConfiguration mail;

  public ValidationCodeConfiguration(final ValidationCodeProperties properties) {
    notNull(properties, "properties");

    this.enable = properties.isEnable();
    this.async = properties.isAsync();
    this.domain = properties.getDomain();
    this.mail = new MailConfiguration(properties.getMail());
  }

  public boolean isEnable() {
    return this.enable;
  }

  public boolean isAsync() {
    return this.async;
  }

  public String getDomain() {
    return this.domain;
  }

  public MailConfiguration getMail() {
    return this.mail;
  }

  @Override
  public String toString() {
    return new StringBuilder()
               .append("{enable=").append(this.enable)
               .append(", async=").append(this.async)
               .append(", domain=").append(singleQuote(this.domain))
               .append(", mail=").append(this.mail)
               .append('}').toString();
  }
}
