package kr.lul.kobalttown.document.data.factory;

import kr.lul.common.data.Context;
import kr.lul.common.data.Creatable;
import kr.lul.common.data.Updatable;
import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.test.AccountTestTool;
import kr.lul.kobalttown.document.data.DocumentDataTestConfiguration;
import kr.lul.kobalttown.document.domain.Document;
import kr.lul.kobalttown.document.domain.History;
import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.document.domain.NoteSnapshot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.List;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/02/09
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DocumentDataTestConfiguration.class)
public class NoteFactoryTest {
  protected static final Logger log = getLogger(NoteFactoryTest.class);

  @Autowired
  private NoteFactory factory;

  @Autowired
  private AccountTestTool accountTestTool;
  @Autowired
  private TimeProvider timeProvider;

  private Instant instant;

  @Before
  public void setUp() throws Exception {
    this.instant = this.timeProvider.now();
    log.info("SETUP - instant={}", this.instant);
  }

  @Test
  public void test_create_with_null_account() throws Exception {
    assertThatThrownBy(() -> this.factory.create(new Context(), null, "body", this.instant))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("author is null.");
  }

  @Test
  public void test_create_with_future_account() throws Exception {
    // GIVEN
    final Account author = this.accountTestTool.account();
    log.info("GIVEN - author={}", author);

    // WHEN & THEN
    assertThatThrownBy(() -> this.factory.create(new Context(), author, "body", author.getCreatedAt().minusMillis(1L)))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("");
  }

  @Test
  public void test_create_with_null_body() throws Exception {
    // GIVEN
    final Account account = this.accountTestTool.account();
    log.info("GIVEN - account={}", account);

    // WHEN & THEN
    assertThatThrownBy(() -> this.factory.create(new Context(), account, null, this.instant))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("body is null.");
  }

  @Test
  public void test_create_with_null_instant() throws Exception {
    // GIVEN
    final Account account = this.accountTestTool.account();
    log.info("GIVEN - account={}", account);
    final String body = random(current().nextInt(10, 100));
    log.info("GIVEN - body={}", body);

    // WHEN & THEN
    assertThatThrownBy(() -> this.factory.create(new Context(), account, body, null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("createdAt is null.");
  }

  @Test
  public void test_create() throws Exception {
    // GIVEN
    final Account account = this.accountTestTool.account();
    log.info("GIVEN - account={}", account);
    final String body = random(current().nextInt(100));
    log.info("GIVEN - body={}", body);
    final Instant createdAt = this.timeProvider.now();
    log.info("GIVEN - createdAt={}", createdAt);

    // WHEN
    final Note note = this.factory.create(new Context(), account, body, createdAt);
    log.info("WHEN - note={}", note);

    // THEN
    assertThat(note)
        .isNotNull()
        .extracting(Document::getId, Document::getVersion, Note::getAuthor, Note::getBody,
            Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(0L, 0, account, body, createdAt, createdAt);

    final History<NoteSnapshot> history = note.history(Integer.MAX_VALUE, 0);
    assertThat(history)
        .isNotNull();
    final List<NoteSnapshot> content = history.content();
    assertThat(content)
        .isNotNull()
        .isEmpty();
  }
}
