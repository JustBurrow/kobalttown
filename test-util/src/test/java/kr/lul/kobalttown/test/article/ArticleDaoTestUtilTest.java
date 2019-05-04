package kr.lul.kobalttown.test.article;

import kr.lul.kobalttown.article.domain.Article;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import test.configuration.TestUtilTestConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-20
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestUtilTestConfiguration.class)
public class ArticleDaoTestUtilTest {
  private static final Logger log = getLogger(ArticleDaoTestUtilTest.class);

  @Autowired
  private ArticleDaoTestUtil articleDaoTestUtil;

  @Before
  public void setUp() throws Exception {
    assertThat(this.articleDaoTestUtil).isNotNull();
  }

  @Test
  public void test_createdArticle() throws Exception {
    // When
    Article article = this.articleDaoTestUtil.createdArticle();
    log.info("WHEN - article={}", article);

    // Then
    assertThat(article)
        .isNotNull();
  }
}