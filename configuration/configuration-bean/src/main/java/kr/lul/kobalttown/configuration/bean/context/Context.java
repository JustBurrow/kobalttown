package kr.lul.kobalttown.configuration.bean.context;

import java.util.UUID;

/**
 * @author justburrow
 * @since 2019/12/07
 */
public class Context {
  protected final UUID id;

  public Context() {
    this(UUID.randomUUID());
  }

  public Context(UUID id) {
    if (null == id)
      throw new IllegalArgumentException("contextId is null.");
    this.id = id;
  }

  public UUID id() {
    return this.id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    return this.id.equals(((Context) o).id);
  }

  @Override
  public int hashCode() {
    return this.id.hashCode();
  }

  @Override
  public String toString() {
    return this.id.toString();
  }
}
