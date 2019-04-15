package test.kr.lul.kobalttown.article.domain;

import static java.util.concurrent.ThreadLocalRandom.current;
import static kr.lul.kobalttown.article.domain.Article.SUMMARY_MAX_LENGTH;
import static kr.lul.kobalttown.article.domain.Article.TITLE_MAX_LENGTH;
import static org.apache.commons.lang3.RandomStringUtils.random;

/**
 * @author justburrow
 * @since 2019-04-03
 */
public class ArticleDomainTestUtil {
  public static String title() {
    return random(current().nextInt(1, 1 + TITLE_MAX_LENGTH));
  }

  public static String summary() {
    return random(current().nextInt(1, 1 + SUMMARY_MAX_LENGTH));
  }

  public static String body() {
    return random(current().nextInt(1, 10000));
  }
}