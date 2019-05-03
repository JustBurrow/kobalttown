package kr.lul.kobalttown.article.borderline;

import kr.lul.kobalttown.account.converter.AccountConverter;
import kr.lul.kobalttown.account.service.AccountService;
import kr.lul.kobalttown.article.borderline.command.CreateArticleCmd;
import kr.lul.kobalttown.article.dto.DetailArticleDto;
import kr.lul.kobalttown.common.util.AssertionException;
import kr.lul.kobalttown.common.util.TimeProvider;
import kr.lul.kobalttown.test.article.ArticleBorderlineTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import test.configuration.ArticleBorderlineTestConfiguration;

import java.time.Instant;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-25
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArticleBorderlineTestConfiguration.class)
public class ArticleBorderlineImplTest {
  private static final Logger log = getLogger(ArticleBorderlineImplTest.class);

  @Autowired
  private ArticleBorderline articleBorderline;
  @Autowired
  private AccountService accountService;
  @Autowired
  private AccountConverter accountConverter;
  @Autowired
  private ArticleBorderlineTestUtil testUtil;
  @Autowired
  private TimeProvider timeProvider;

  private ZonedDateTime before;

  @Before
  public void setUp() throws Exception {
    assertThat(this.articleBorderline).isNotNull();
    assertThat(this.accountService).isNotNull();
    assertThat(this.accountConverter).isNotNull();
    assertThat(this.testUtil).isNotNull();
    assertThat(this.timeProvider).isNotNull();

    this.before = this.timeProvider.zonedDateTime();
  }

  @Test
  public void test_create_with_null() throws Exception {
    assertThatThrownBy(() -> this.articleBorderline.create(null))
        .isInstanceOf(AssertionException.class)
        .hasMessage("cmd is null.");
  }

  @Test
  public void test_create() throws Exception {
    // Given
    CreateArticleCmd cmd = this.testUtil.createArticleCmd();
    int creator = cmd.getCreator();
    String title = cmd.getTitle();
    String body = cmd.getBody();
    Instant timestamp = cmd.getTimestamp();
    log.info("GIVEN - cmd={}", cmd);

    // When
    DetailArticleDto dto = this.articleBorderline.create(cmd);
    log.info("WHEN - dto={}", dto);

    // Then
    assertThat(dto)
        .isNotNull()
        .extracting(DetailArticleDto::getTitle, DetailArticleDto::getBody,
            DetailArticleDto::getAuthor, DetailArticleDto::getCreatedAt)
        .containsSequence(title, body,
            this.accountConverter.simple(this.accountService.read(creator)),
            this.timeProvider.zonedDateTime(timestamp));
    assertThat(dto.getId())
        .isGreaterThan(0L);
    assertThat(dto.getBody())
        .isNotNull()
        .startsWith(dto.getSummary());
  }
}