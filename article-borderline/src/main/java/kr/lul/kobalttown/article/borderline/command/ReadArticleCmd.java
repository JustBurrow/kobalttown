package kr.lul.kobalttown.article.borderline.command;

import kr.lul.kobalttown.article.domain.Article;
import kr.lul.kobalttown.support.spring.security.AccountTrackingContext;

import java.time.Instant;
import java.util.StringJoiner;

/**
 * @author justburrow
 * @since 2019-05-04
 */
public class ReadArticleCmd extends AccountTrackingContext {
  /**
   * 글 ID.
   *
   * @see Article#getId()
   */
  private long article;

  public ReadArticleCmd(Instant timestamp, int account) {
    super(timestamp, account);
  }

  public ReadArticleCmd(Instant timestamp, int account, long article) {
    super(timestamp, account);
    this.article = article;
  }

  public long getArticle() {
    return this.article;
  }

  public void setArticle(long article) {
    this.article = article;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return new StringJoiner(", ", ReadArticleCmd.class.getSimpleName() + "[", "]")
        .add("trackingId=" + this.trackingId)
        .add("timestamp=" + this.timestamp)
        .add("account=" + this.account)
        .add("article=" + this.article)
        .toString();
  }
}