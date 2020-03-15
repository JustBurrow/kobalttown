package kr.lul.kobalttown.document.web.controller.request;

/**
 * @author justburrow
 * @since 2020/03/14
 */
public class ListNoteReq {
  public static final int MIN_LIMIT = 10;
  /**
   * 페이지. 0-based.
   */
  private int page;
  /**
   * 페이지 최대 크기.
   */
  private int limit;

  public ListNoteReq() {
    this(0, MIN_LIMIT);
  }

  public ListNoteReq(final int page, final int limit) {
    setPage(page);
    setLimit(limit);
  }

  public int getPage() {
    return this.page;
  }

  public void setPage(final int page) {
    this.page = page;
  }

  public int getLimit() {
    return this.limit;
  }

  public void setLimit(final int limit) {
    this.limit = Math.max(MIN_LIMIT, limit);
  }

  @Override
  public String toString() {
    return new StringBuilder()
               .append("{page=").append(this.page)
               .append(", limit=").append(this.limit)
               .append('}').toString();
  }
}
