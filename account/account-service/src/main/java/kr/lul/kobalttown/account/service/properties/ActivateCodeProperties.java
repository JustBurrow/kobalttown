package kr.lul.kobalttown.account.service.properties;

import javax.validation.constraints.NotEmpty;

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
   * 발신자 email 주소. 'dev@lul.kr' 혹은 'Kobalttown Dev<dev@lul.kr>' 형식.
   */
  @NotEmpty
  private String from;
  /**
   * 제목
   */
  @NotEmpty
  private String title;
  /**
   * 메일 본문 템플릿 이름.
   */
  @NotEmpty
  private String messageTemplate;
  /**
   * 도메인 주소. 이 주소를 기반으로 계정활성화 URL을 만든다.
   */
  @NotEmpty
  private String domain;

  public boolean isEnable() {
    return this.enable;
  }

  public void setEnable(final boolean enable) {
    this.enable = enable;
  }

  public String getFrom() {
    return this.from;
  }

  public void setFrom(final String from) {
    this.from = from;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public String getMessageTemplate() {
    return this.messageTemplate;
  }

  public void setMessageTemplate(final String messageTemplate) {
    this.messageTemplate = messageTemplate;
  }

  public String getDomain() {
    return this.domain;
  }

  public void setDomain(final String domain) {
    this.domain = domain;
  }

  @Override
  public String toString() {
    return new StringBuilder()
        .append("{enable=").append(this.enable)
        .append(", from=").append(singleQuote(this.from))
        .append(", title=").append(singleQuote(this.title))
        .append(", messageTemplate=").append(singleQuote(this.messageTemplate))
        .append(", domain=").append(singleQuote(this.domain))
        .append('}').toString();
  }
}
