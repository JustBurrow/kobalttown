package kr.lul.kobalttown.article.service.params;

import kr.lul.kobalttown.support.spring.security.AccountTrackingContext;

import java.util.StringJoiner;

import static kr.lul.kobalttown.common.util.Arguments.notNegative;
import static kr.lul.kobalttown.common.util.Arguments.positive;

/**
 * @author justburrow
 * @since 2019-05-04
 */
public class ListArticleParams extends AccountTrackingContext {
  private int page;
  private int pageSize;

  public ListArticleParams(AccountTrackingContext ctx, int page, int pageSize) {
    super(ctx);
    notNegative(page, "page");
    positive(pageSize, "pageSize");

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
    return new StringJoiner(", ", ListArticleParams.class.getSimpleName() + "[", "]")
        .add("trackingId=" + this.trackingId)
        .add("timestamp=" + this.timestamp)
        .add("account=" + this.account)
        .add("page=" + this.page)
        .add("pageSize=" + this.pageSize)
        .toString();
  }
}