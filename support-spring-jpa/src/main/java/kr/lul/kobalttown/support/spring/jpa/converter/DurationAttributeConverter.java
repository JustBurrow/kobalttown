package kr.lul.kobalttown.support.spring.jpa.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Duration;

/**
 * @author justburrow
 * @since 2019-02-28
 */
@Converter(autoApply = true)
public class DurationAttributeConverter implements AttributeConverter<Duration, String> {
  @Override
  public String convertToDatabaseColumn(Duration duration) {
    return null == duration
        ? null
        : duration.toString();
  }

  @Override
  public Duration convertToEntityAttribute(String text) {
    return null == text
        ? null
        : Duration.parse(text);
  }
}