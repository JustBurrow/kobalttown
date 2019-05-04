package kr.lul.kobalttown.test.article;

import kr.lul.kobalttown.article.borderline.command.CreateArticleCmd;

import static java.util.UUID.randomUUID;

/**
 * @author justburrow
 * @since 2019-04-25
 */
public class ArticleBorderlineTestUtil extends ArticleServiceTestUtil {
  public CreateArticleCmd createArticleCmd() {
    return new CreateArticleCmd(randomUUID(), this.timeProvider.now(),
        this.accountServiceTestUtil.createdAccount().getId(),
        title(), body());
  }
}