package kr.lul.kobalttown.document.service.params;

import kr.lul.common.data.Context;
import kr.lul.common.data.ContextContainer;
import kr.lul.kobalttown.account.domain.Account;

import java.time.Instant;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2020/02/11
 */
public class ReadNoteParams extends ContextContainer {
  /**
   * 노트를 요청한 유저.
   */
  private Account user;
  /**
   * 요청한 노트 ID.
   */
  private long id;
  /**
   * 요청 시각.
   */
  private Instant timestamp;

  public ReadNoteParams(final ContextContainer container, final Account user, final long id, final Instant timestamp) {
    this(container.getContext(), user, id, timestamp);
  }

  public ReadNoteParams(final Context context, final Account user, final long id, final Instant timestamp) {
    super(context);
    notNull(user, "user");
    notNull(timestamp, "timestamp");

    this.user = user;
    this.id = id;
    this.timestamp = timestamp;
  }

  public Account getUser() {
    return this.user;
  }

  public long getId() {
    return this.id;
  }

  public Instant getTimestamp() {
    return this.timestamp;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return format("{context=%s, user=%s, id=%d, timestamp=%s}}",
        this.context, this.user.toSimpleString(), this.id, this.timestamp);
  }
}
