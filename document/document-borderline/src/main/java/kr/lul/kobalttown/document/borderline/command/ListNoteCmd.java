package kr.lul.kobalttown.document.borderline.command;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.transfer.account.UserCmd;

import java.time.Instant;

import static kr.lul.common.util.Arguments.notNegative;
import static kr.lul.common.util.Arguments.positive;

/**
 * @author justburrow
 * @since 2020/03/14
 */
public class ListNoteCmd extends UserCmd {
  private int page;
  private int limit;

  public ListNoteCmd(final Context context, final long user, final int page, final int limit, final Instant timestamp) {
    super(context, user, timestamp);
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
    return new StringBuilder()
               .append("{id=").append(this.id)
               .append(", user=").append(this.user)
               .append(", page=").append(this.page)
               .append(", limit=").append(this.limit)
               .append(", timestamp=").append(this.timestamp)
               .append('}').toString();
  }
}
