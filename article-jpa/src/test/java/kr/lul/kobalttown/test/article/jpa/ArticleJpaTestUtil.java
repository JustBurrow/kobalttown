package kr.lul.kobalttown.test.article.jpa;

import kr.lul.kobalttown.account.jpa.entity.AccountEntity;
import kr.lul.kobalttown.article.jpa.entity.ArticleEntity;
import kr.lul.kobalttown.common.util.TimeProvider;
import kr.lul.kobalttown.test.account.jpa.AccountJpaTestUtil;
import kr.lul.kobalttown.test.article.domain.ArticleDomainTestUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

import static kr.lul.kobalttown.common.util.Arguments.notEmpty;
import static kr.lul.kobalttown.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-01
 */
public class ArticleJpaTestUtil extends ArticleDomainTestUtil {
  private static final Logger log = getLogger(ArticleJpaTestUtil.class);

  @Autowired
  private AccountJpaTestUtil accountJpaTestUtil;
  @Autowired
  protected TimeProvider timeProvider;

  /**
   * 저장하지 않은 작성자를 사용해 새 글을 만든다.
   *
   * @return 저장하지 않은 새 글.
   */
  public ArticleEntity prePersistArticle() {
    return prePersistArticle(this.accountJpaTestUtil.persistedAccount());
  }

  /**
   * 저장하지 않은 새 글을 만든다.
   *
   * @param author 작성자. 저장 여부에 관여하지 않는다.
   *
   * @return 저장하지 않은 새 글.
   */
  private ArticleEntity prePersistArticle(AccountEntity author) {
    return prePersistArticle(title(), body(), author, this.timeProvider.now());
  }

  private ArticleEntity prePersistArticle(String title, String body, AccountEntity author, Instant createdAt) {
    notEmpty(title, "title");
    notEmpty(body, "body");
    notNull(author, "author");
    notNull(createdAt, "createdAt");

    return new ArticleEntity(title, body, author, createdAt);
  }
}