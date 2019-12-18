package kr.lul.kobalttown.account.service.configuration;

import kr.lul.kobalttown.account.service.properties.ActivationProperties;

import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2019/12/18
 */
public class ActivationConfiguration {
  private String from;
  private String title;
  private String template;
  private String domain;

  public ActivationConfiguration(final ActivationProperties properties) {
    notNull(properties, "properties");

    this.from = properties.getFrom();
    this.title = properties.getTitle();
    this.template = properties.getMessageTemplate();
    this.domain = properties.getDomain();
  }

  public String getFrom() {
    return this.from;
  }

  public String getTitle() {
    return this.title;
  }

  public String getTemplate() {
    return this.template;
  }

  public String getDomain() {
    return this.domain;
  }

  @Override
  public String toString() {
    return new StringBuilder()
        .append("{from=").append(singleQuote(this.from))
        .append(", title=").append(singleQuote(this.title))
        .append(", template=").append(singleQuote(this.template))
        .append(", domain=").append(singleQuote(this.domain))
        .append('}').toString();
  }
}
