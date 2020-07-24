package kr.lul.kobalttown.configuration.web;

import kr.lul.kobalttown.configuration.web.properties.MessageProperties;

import java.io.File;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2020/07/25
 */
public class MessageConfig {
  private Charset encoding;
  private Duration cacheDuration;
  private List<File> basenames;

  public MessageConfig(MessageProperties properties) {
    notNull(properties, "properties");
    notNull(properties.getCacheDuration(), "properties.cacheDuration");
    notNull(properties.getEncoding(), "properties.encoding");

    this.encoding = properties.getEncoding();
    this.cacheDuration = properties.getCacheDuration();
    this.basenames = null == properties.getBasenames()
                         ? emptyList()
                         : unmodifiableList(properties.getBasenames());
  }

  public Charset getEncoding() {
    return this.encoding;
  }

  public Duration getCacheDuration() {
    return this.cacheDuration;
  }

  public List<File> getBasenames() {
    return this.basenames;
  }

  @Override
  public String toString() {
    return new StringBuilder('{')
               .append("encoding=").append(this.encoding)
               .append(", cacheDuration=").append(this.cacheDuration)
               .append(", basenames=").append(this.basenames)
               .append('}').toString();
  }
}
