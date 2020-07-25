package kr.lul.kobalttown.document.web.controller.request;

/**
 * @author justburrow
 * @since 2020/07/24
 */
public class ReadNoteReq {
  private PaginationReq comments;

  public ReadNoteReq() {
    this.comments = new PaginationReq();
  }

  public PaginationReq getComments() {
    return this.comments;
  }

  public void setComments(PaginationReq comments) {
    this.comments = comments;
  }

  @Override
  public String toString() {
    return new StringBuilder('{')
               .append("comments=").append(this.comments)
               .append('}').toString();
  }
}
