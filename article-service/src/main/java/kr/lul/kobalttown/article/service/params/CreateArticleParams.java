package kr.lul.kobalttown.article.service.params;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.common.util.AbstractTrackingContext;
import kr.lul.kobalttown.common.util.TrackingContext;

import java.time.Instant;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * @author justburrow
 * @since 2019-04-20
 */
public class CreateArticleParams extends AbstractTrackingContext<UUID> {
  private Account creator;
  private String title;
  private String body;


  public CreateArticleParams(TrackingContext<UUID> trackingId, Account creator, String title, String body) {
    this(trackingId.getTrackingId(), trackingId.getTimestamp(), creator, title, body);
  }

  public CreateArticleParams(UUID trackingId, Instant timestamp, Account creator, String title, String body) {
    super(trackingId, timestamp);

    this.creator = creator;
    this.title = title;
    this.body = body;
  }

  public Account getCreator() {
    return this.creator;
  }

  public void setCreator(Account creator) {
    this.creator = creator;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return this.body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", CreateArticleParams.class.getSimpleName() + "[", "]")
        .add("creator=" + this.creator)
        .add("title='" + this.title + "'")
        .add("body='" + this.body + "'")
        .toString();
  }
}