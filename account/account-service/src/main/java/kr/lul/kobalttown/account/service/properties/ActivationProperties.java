package kr.lul.kobalttown.account.service.properties;

import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2019/12/18
 */
public class ActivationProperties {
  private String from;
  private String title;
  private String messageTemplate;
  private String domain;

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
        .append("{from=").append(singleQuote(this.from))
        .append(", title=").append(singleQuote(this.title))
        .append(", messageTemplate=").append(singleQuote(this.messageTemplate))
        .append(", domain=").append(singleQuote(this.domain))
        .append('}').toString();
  }
}
