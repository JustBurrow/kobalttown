package kr.lul.kobalttown.configuration.web.properties;

import java.io.File;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.List;

/**
 * @author justburrow
 * @since 2020/07/25
 */
public class MessageProperties {
  private Charset encoding;
  private Duration cacheDuration;
  private List<File> basenames;

  public MessageProperties() {
  }

  public Charset getEncoding() {
    return this.encoding;
  }

  public void setEncoding(Charset encoding) {
    this.encoding = encoding;
  }

  public Duration getCacheDuration() {
    return this.cacheDuration;
  }

  public void setCacheDuration(Duration cacheDuration) {
    this.cacheDuration = cacheDuration;
  }

  public List<File> getBasenames() {
    return this.basenames;
  }

  public void setBasenames(List<File> basenames) {
    this.basenames = basenames;
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
