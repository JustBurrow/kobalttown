package kr.lul.kobalttown.document.borderline.command;

import kr.lul.common.data.Context;
import kr.lul.common.data.ContextContainer;

import java.time.Instant;

/**
 * @author justburrow
 * @since 2020/03/14
 */
public class ListNoteCmd extends ContextContainer {
  private long user;
  private int page;
  private int limit;
  private Instant timestamp;

  public ListNoteCmd(final Context context, final long user, final int page, final int limit, final Instant timestamp) {
    super(context);
    this.user = user;
    this.page = page;
    this.limit = limit;
    this.timestamp = timestamp;
  }

  public ListNoteCmd(final ContextContainer container, final long user, final int page, final int limit,
      final Instant timestamp) {
    this(container.getContext(), user, page, limit, timestamp);
  }

  public long getUser() {
    return this.user;
  }

  public int getPage() {
    return this.page;
  }

  public int getLimit() {
    return this.limit;
  }

  public Instant getTimestamp() {
    return this.timestamp;
  }

  @Override
  public String toString() {
    return new StringBuilder()
               .append("{context=").append(this.context)
               .append(", user=").append(this.user)
               .append(", page=").append(this.page)
               .append(", limit=").append(this.limit)
               .append(", timestamp=").append(this.timestamp)
               .append('}').toString();
  }
}
