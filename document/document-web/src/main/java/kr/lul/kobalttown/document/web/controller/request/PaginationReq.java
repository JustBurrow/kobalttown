package kr.lul.kobalttown.document.web.controller.request;

/**
 * @author justburrow
 * @since 2020/07/25
 */
public class PaginationReq {
  public static final int DEFAULT_PAGE = 0;
  public static final int DEFAULT_SIZE = 20;

  private int page;
  private int size;

  public PaginationReq() {
    this(DEFAULT_PAGE, DEFAULT_SIZE);
  }

  public PaginationReq(int page, int size) {
    this.page = page;
    this.size = size;
  }

  public int getPage() {
    return this.page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getSize() {
    return this.size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  @Override
  public String toString() {
    return new StringBuilder(PaginationReq.class.getSimpleName())
               .append("page=").append(this.page)
               .append(", size=").append(this.size)
               .append('}').toString();
  }
}
