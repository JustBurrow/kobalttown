package kr.lul.common.util;

import java.util.Objects;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * 컨버터를 구분할 수 있는 ID.
 * JPA 등에서 자주 사용하는 `ID`, `Id`는 이름 충돌을 고려해 사용 안함.
 *
 * @author justburrow
 * @since 2018. 9. 24.
 */
public class ConverterIdentifier implements Identifier {
  protected final Class source;
  protected final Class target;

  public ConverterIdentifier(Class source, Class target) {
    requireNonNull(source, "source is null.");
    this.source = source;
    this.target = target;
  }

  public Class source() {
    return this.source;
  }

  public Class target() {
    return this.target;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ConverterIdentifier)) return false;
    ConverterIdentifier id = (ConverterIdentifier) o;
    return Objects.equals(this.source, id.source) &&
        Objects.equals(this.target, id.target);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.source, this.target);
  }

  @Override
  public String toString() {
    return format("%s -> %s", this.source.getName(), this.target.getName());
  }
}
