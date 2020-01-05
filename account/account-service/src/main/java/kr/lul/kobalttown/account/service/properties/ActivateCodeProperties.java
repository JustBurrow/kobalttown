package kr.lul.kobalttown.account.service.properties;

import kr.lul.support.spring.mail.MailProperties;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2019/12/18
 */
public class ActivateCodeProperties {
  /**
   * 계정 인증 코드를 사용할지 여부.
   */
  private boolean enable;
  /**
   * 게정 인증 코드 발송을 기다린다.
   * 발송이 끝나야 계정 등록 로직을 완료한다.
   */
  private boolean async;
  /**
   * 도메인 주소. 이 주소를 기반으로 계정활성화 URL을 만든다.
   */
  @NotEmpty
  private String domain;
  @NotNull
  private MailProperties mail;

  public boolean isEnable() {
    return this.enable;
  }

  public void setEnable(final boolean enable) {
    this.enable = enable;
  }

  public boolean isAsync() {
    return this.async;
  }

  public void setAsync(final boolean async) {
    this.async = async;
  }

  public String getDomain() {
    return this.domain;
  }

  public void setDomain(final String domain) {
    this.domain = domain;
  }

  public MailProperties getMail() {
    return this.mail;
  }

  public void setMail(final MailProperties mail) {
    this.mail = mail;
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
