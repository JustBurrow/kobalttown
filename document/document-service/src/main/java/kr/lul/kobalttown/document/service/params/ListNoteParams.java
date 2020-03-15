package kr.lul.kobalttown.document.service.params;

import kr.lul.common.data.Context;
import kr.lul.common.data.ContextContainer;
import kr.lul.kobalttown.account.domain.Account;

import java.time.Instant;

/**
 * @author justburrow
 * @since 2020/03/14
 */
public class ListNoteParams extends ContextContainer {
  private Account user;
  private int page;
  private int limit;
  private Instant timestamp;

  public ListNoteParams(final Context context, final Account user, final int page, final int limit, final Instant timestamp) {
    super(context);
    this.user = user;
    this.page = page;
    this.limit = limit;
    this.timestamp = timestamp;
  }

  public ListNoteParams(final ContextContainer container, final Account user, final int page, final int limit,
      final Instant timestamp) {
    this(container.getContext(), user, page, limit, timestamp);
  }

  public Account getUser() {
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
    return new StringBuilder("{context=").append(this.context)
               .append(", user=").append(this.user)
               .append(", page=").append(this.page)
               .append(", limit=").append(this.limit)
               .append(", timestamp=").append(this.timestamp)
               .append('}').toString();
  }
}
