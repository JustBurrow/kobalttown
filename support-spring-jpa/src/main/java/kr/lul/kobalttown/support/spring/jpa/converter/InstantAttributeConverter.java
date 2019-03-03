package kr.lul.kobalttown.support.spring.jpa.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;

/**
 * @author justburrow
 * @since 2019-02-27
 */
@Converter(autoApply = true)
public class InstantAttributeConverter implements AttributeConverter<Instant, Long> {
  @Override
  public Long convertToDatabaseColumn(Instant instant) {
    return null == instant
        ? null
        : instant.toEpochMilli();
  }

  @Override
  public Instant convertToEntityAttribute(Long epochMillis) {
    return null == epochMillis
        ? null
        : Instant.ofEpochMilli(epochMillis);
  }
}