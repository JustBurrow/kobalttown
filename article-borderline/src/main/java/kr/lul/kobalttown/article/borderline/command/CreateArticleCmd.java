package kr.lul.kobalttown.article.borderline.command;

import kr.lul.kobalttown.common.util.AbstractTrackingContext;

import java.time.Instant;
import java.util.StringJoiner;
import java.util.UUID;

/**
 * @author justburrow
 * @since 2019-04-25
 */
public class CreateArticleCmd extends AbstractTrackingContext<UUID> {
  private int creator;
  private String title;
  private String body;

  public CreateArticleCmd(UUID trackingId, Instant timestamp) {
    super(trackingId, timestamp);
  }

  public CreateArticleCmd(UUID trackingId, Instant timestamp, int creator, String title, String body) {
    super(trackingId, timestamp);
    this.creator = creator;
    this.title = title;
    this.body = body;
  }

  public int getCreator() {
    return this.creator;
  }

  public void setCreator(int creator) {
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

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return new StringJoiner(", ", CreateArticleCmd.class.getSimpleName() + "[", "]")
        .add("trackingId=" + this.trackingId)
        .add("timestamp=" + this.timestamp)
        .add("creator=" + this.creator)
        .add("title='" + this.title + "'")
        .add("body='" + this.body + "'")
        .toString();
  }
}