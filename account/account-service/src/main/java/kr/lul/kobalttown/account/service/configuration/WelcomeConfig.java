package kr.lul.kobalttown.account.service.configuration;

import kr.lul.kobalttown.account.service.properties.WelcomeProperties;
import kr.lul.support.spring.mail.MailConfiguration;

import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2019/12/27
 */
public class WelcomeConfig {
  /**
   * 가입 안내를 할지 여부.
   */
  private boolean enable;
  /**
   * 가입 안내 알림 메시지(메일) 전송을 기다름.
   * 메시지 전송이 끝나야 계정 등록 로직을 완료한다.
   */
  private boolean async;
  private MailConfiguration mail;

  public WelcomeConfig(final WelcomeProperties properties) {
    notNull(properties, "properties");

    this.enable = properties.isEnable();
    this.async = properties.isAsync();
    this.mail = new MailConfiguration(properties.getMail());
  }

  public boolean isEnable() {
    return this.enable;
  }

  public boolean isAsync() {
    return this.async;
  }

  public MailConfiguration getMail() {
    return this.mail;
  }

  @Override
  public String toString() {
    return new StringBuilder('{')
               .append("enable=").append(this.enable)
               .append(", async=").append(this.async)
               .append(", mail=").append(this.mail)
               .append('}').toString();
  }
}
