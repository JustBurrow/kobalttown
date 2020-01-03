package kr.lul.kobalttown.account.service.params;

import kr.lul.common.data.Context;
import kr.lul.common.data.ContextContainer;

import java.time.Instant;
import java.util.Objects;

import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Arguments.positive;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public class ReadAccountParams extends ContextContainer {
  private long id;
  private Instant timestamp;

  public ReadAccountParams(Context context, long id, Instant timestamp) {
    super(context);
    positive(id, "id");
    notNull(timestamp, "timestamp");

    this.id = id;
    this.timestamp = timestamp;
  }

  public long getId() {
    return this.id;
  }

  public Instant getTimestamp() {
    return this.timestamp;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    ReadAccountParams that = (ReadAccountParams) o;
    return this.context.equals(that.context) &&
        this.id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.context, this.id);
  }

  @Override
  public String toString() {
    return new StringBuilder(ReadAccountParams.class.getSimpleName())
        .append("{context=").append(this.context)
        .append(", id=").append(this.id)
        .append(", timestamp=").append(this.timestamp)
        .append('}')
        .toString();
  }
}
