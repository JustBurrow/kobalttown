package kr.lul.kobalttown.account.service.configuration;

import kr.lul.kobalttown.account.service.properties.ActivateCodeProperties;

import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2019/12/18
 */
public class ActivateCodeConfiguration {
  private boolean enable;
  private String from;
  private String title;
  private String template;
  private String domain;

  public ActivateCodeConfiguration(final ActivateCodeProperties properties) {
    notNull(properties, "properties");

    this.enable = properties.isEnable();
    this.from = properties.getFrom();
    this.title = properties.getTitle();
    this.template = properties.getMessageTemplate();
    this.domain = properties.getDomain();
  }

  public boolean isEnable() {
    return this.enable;
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
        .append("{enable=").append(this.enable)
        .append(", from=").append(singleQuote(this.from))
        .append(", title=").append(singleQuote(this.title))
        .append(", template=").append(singleQuote(this.template))
        .append(", domain=").append(singleQuote(this.domain))
        .append('}').toString();
  }
}
