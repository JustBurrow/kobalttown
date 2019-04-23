package kr.lul.kobalttown.test.article;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.article.service.params.CreateArticleParams;
import kr.lul.kobalttown.common.util.TimeProvider;
import kr.lul.kobalttown.test.account.AccountServiceTestUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static java.util.UUID.randomUUID;

/**
 * @author justburrow
 * @since 2019-04-20
 */
public class ArticleServiceTestUtil extends ArticleDaoTestUtil {
  @Autowired
  private AccountServiceTestUtil accountServiceTestUtil;
  @Autowired
  private TimeProvider timeProvider;

  public CreateArticleParams createArticleParams() {
    return createArticleParams(randomUUID());
  }

  private CreateArticleParams createArticleParams(UUID trackingId) {
    return createArticleParams(trackingId, this.accountServiceTestUtil.createdAccount());
  }

  private CreateArticleParams createArticleParams(UUID trackingId, Account creator) {
    return new CreateArticleParams(trackingId, this.timeProvider.now(), creator, title(), body());
  }
}