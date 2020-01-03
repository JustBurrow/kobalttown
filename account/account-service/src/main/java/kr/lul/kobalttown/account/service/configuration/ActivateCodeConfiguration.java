package kr.lul.kobalttown.account.service.configuration;

import kr.lul.kobalttown.account.service.properties.ActivateCodeProperties;
import kr.lul.support.spring.mail.MailConfiguration;

import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2019/12/18
 */
public class ActivateCodeConfiguration {
  private boolean enable;
  private boolean wait;
  private String domain;
  private MailConfiguration mail;

  public ActivateCodeConfiguration(final ActivateCodeProperties properties) {
    notNull(properties, "properties");

    this.enable = properties.isEnable();
    this.wait = properties.isWait();
    this.domain = properties.getDomain();
    this.mail = new MailConfiguration(properties.getMail());
  }

  public boolean isEnable() {
    return this.enable;
  }

  public boolean isWait() {
    return this.wait;
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
               .append(", wait=").append(this.wait)
               .append(", domain=").append(singleQuote(this.domain))
               .append(", mail=").append(this.mail)
               .append('}').toString();
  }
}
