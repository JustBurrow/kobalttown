package kr.lul.kobalttown.article.dao;

import kr.lul.kobalttown.common.util.AssertionException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-12
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArticleDaoTestConfiguration.class)
@Transactional
public class ArticleDaoImplTest {
  private static final Logger log = getLogger(ArticleDaoImplTest.class);

  @Autowired
  private ArticleDao articleDao;

  @Before
  public void setUp() throws Exception {
    assertThat(this.articleDao).isNotNull();
  }

  @Test
  public void test_create_with_null() throws Exception {
    assertThatThrownBy(() -> this.articleDao.create(null))
        .isInstanceOf(AssertionException.class)
        .hasMessage("article is null.");
  }
}