package kr.lul.kobalttown.test.article.jpa;

import kr.lul.kobalttown.account.jpa.entity.AccountEntity;
import kr.lul.kobalttown.article.jpa.entity.ArticleEntity;
import kr.lul.kobalttown.test.account.jpa.AccountJpaTestUtil;
import kr.lul.kobalttown.test.article.domain.ArticleDomainTestUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-01
 */
public class ArticleJpaTestUtil extends ArticleDomainTestUtil {
  private static final Logger log = getLogger(ArticleJpaTestUtil.class);

  @Autowired
  private AccountJpaTestUtil accountJpaTestUtil;

  /**
   * 저장하지 않은 작성자를 사용해 새 글을 만든다.
   *
   * @return 저장하지 않은 새 글.
   */
  public ArticleEntity prePersistArticle() {
    return prePersistArticle(this.accountJpaTestUtil.prePersistAccount());
  }

  /**
   * 저장하지 않은 새 글을 만든다.
   *
   * @param author 작성자. 저장 여부에 관여하지 않는다.
   *
   * @return 저장하지 않은 새 글.
   */
  private ArticleEntity prePersistArticle(AccountEntity author) {
    return null;
  }
}