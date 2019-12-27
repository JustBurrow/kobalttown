package kr.lul.kobalttown.account.service.configuration;

import kr.lul.kobalttown.account.service.properties.WelcomeProperties;
import kr.lul.support.spring.mail.MailConfiguration;

import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2019/12/27
 */
public class WelcomeConfiguration {
  private boolean enable;
  private MailConfiguration mail;

  public WelcomeConfiguration(final WelcomeProperties properties) {
    notNull(properties, "properties");

    this.enable = properties.isEnable();
    this.mail = new MailConfiguration(properties.getMail());
  }

  public boolean isEnable() {
    return this.enable;
  }

  public MailConfiguration getMail() {
    return this.mail;
  }

  @Override
  public String toString() {
    return new StringBuilder('{')
        .append("enable=").append(this.enable)
        .append(", mail=").append(this.mail)
        .append('}').toString();
  }
}
