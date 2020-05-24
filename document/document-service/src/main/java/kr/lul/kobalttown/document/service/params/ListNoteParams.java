package kr.lul.kobalttown.document.service.params;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.transfer.account.UserParams;

import java.time.Instant;
import java.util.UUID;

import static kr.lul.common.util.Arguments.*;

/**
 * @author justburrow
 * @since 2020/03/14
 */
public class ListNoteParams extends UserParams {
  private int page;
  private int limit;

  public ListNoteParams(final Context context, final Account user, final int page, final int limit, final Instant timestamp) {
    this(context.getId(), user, page, limit, timestamp);
  }

  public ListNoteParams(final UUID context, final Account user, final int page, final int limit, final Instant timestamp) {
    super(context, user, timestamp);
    notNull(user, "user");
    notNegative(page, "page");
    positive(limit, "limit");

    this.page = page;
    this.limit = limit;
  }

  public int getPage() {
    return this.page;
  }

  public int getLimit() {
    return this.limit;
  }

  @Override
  public String toString() {
    return new StringBuilder("{id=").append(this.id)
               .append(", user=").append(this.user.toSimpleString())
               .append(", page=").append(this.page)
               .append(", limit=").append(this.limit)
               .append(", timestamp=").append(this.timestamp)
               .append('}').toString();
  }
}
