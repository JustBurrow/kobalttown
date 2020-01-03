package kr.lul.kobalttown.account.service.properties;

import kr.lul.support.spring.mail.MailProperties;

/**
 * @author justburrow
 * @since 2019/12/27
 */
public class WelcomeProperties {
  private boolean enable;
  private boolean wait;
  private MailProperties mail;

  public boolean isEnable() {
    return this.enable;
  }

  public void setEnable(final boolean enable) {
    this.enable = enable;
  }

  public boolean isWait() {
    return this.wait;
  }

  public void setWait(final boolean wait) {
    this.wait = wait;
  }

  public MailProperties getMail() {
    return this.mail;
  }

  public void setMail(final MailProperties mail) {
    this.mail = mail;
  }

  @Override
  public String toString() {
    return new StringBuilder('{')
               .append("enable=").append(this.enable)
               .append(", wait=").append(this.wait)
               .append(", mail=").append(this.mail)
               .append('}').toString();
  }
}
