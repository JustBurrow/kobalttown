package kr.lul.kobalttown.article.borderline.command;

import kr.lul.kobalttown.support.spring.security.AccountDetails;
import kr.lul.kobalttown.support.spring.security.AccountTrackingContext;

import java.time.Instant;
import java.util.StringJoiner;

/**
 * @author justburrow
 * @since 2019-05-04
 */
public class ListArticlecmd extends AccountTrackingContext {
  /**
   * 0-based.
   */
  private int page;
  private int pageSize;

  public ListArticlecmd(Instant timestamp, AccountDetails account, int page, int pageSize) {
    super(timestamp, account);
    this.page = page;
    this.pageSize = pageSize;
  }

  public int getPage() {
    return this.page;
  }

  public int getPageSize() {
    return this.pageSize;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return new StringJoiner(", ", ListArticlecmd.class.getSimpleName() + "[", "]")
        .add("trackingId=" + this.trackingId)
        .add("timestamp=" + this.timestamp)
        .add("account=" + this.account)
        .add("page=" + this.page)
        .add("pageSize=" + this.pageSize)
        .toString();
  }
}