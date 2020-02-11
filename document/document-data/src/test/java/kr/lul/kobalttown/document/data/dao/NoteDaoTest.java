package kr.lul.kobalttown.document.data.dao;

import kr.lul.common.data.Context;
import kr.lul.common.data.Creatable;
import kr.lul.common.data.Updatable;
import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.test.AccountTestTool;
import kr.lul.kobalttown.document.data.DocumentDataTestConfiguration;
import kr.lul.kobalttown.document.data.factory.NoteFactory;
import kr.lul.kobalttown.document.domain.Document;
import kr.lul.kobalttown.document.domain.Note;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;

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
@Transactional
public class NoteDaoTest {
  protected static final Logger log = getLogger(NoteDaoTest.class);

  @Autowired
  private NoteDao dao;
  @Autowired
  private NoteFactory factory;

  @Autowired
  private AccountTestTool accountTestTool;
  @Autowired
  private TimeProvider timeProvider;
  @Autowired
  private EntityManager entityManager;

  @Test
  public void test_create_with_null_context() throws Exception {
    // GIVEN
    final Account account = this.accountTestTool.account();
    log.info("GIVEN - account={}", account);
    final String body = random(current().nextInt(100));
    log.info("GIVEN - body={}", body);
    final Note note = this.factory.create(new Context(), account, body, this.timeProvider.now());
    log.info("GIVEN - note={}", note);

    // WHEN & THEN
    assertThatThrownBy(() -> this.dao.create(null, note))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("context is null.");
  }

  @Test
  public void test_create_with_null_note() throws Exception {
    assertThatThrownBy(() -> this.dao.create(new Context(), null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("note is null.");
  }

  @Test
  public void test_create() throws Exception {
    // GIVEN
    final Account account = this.accountTestTool.account();
    log.info("GIVEN - account={}", account);
    final String body = random(current().nextInt(100));
    log.info("GIVEN - body={}", body);
    final Instant createdAt = this.timeProvider.now();
    final Note note = this.factory.create(new Context(), account, body, createdAt);
    log.info("GIVEN - note={}", note);

    // WHEN
    final Note created = this.dao.create(new Context(), note);
    log.info("WHEN - created={}", created);

    // THEN
    assertThat(created)
        .isNotNull()
        .extracting(Document::getVersion, Note::getAuthor, Note::getBody, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(0, account, body, createdAt, createdAt);
    assertThat(created.getId())
        .isPositive();
  }

  @Test
  public void test_read_with_negative1() throws Exception {
    assertThat(this.dao.read(new Context(), -1L))
        .isNull();
  }

  @Test
  public void test_read_with_0() throws Exception {
    assertThat(this.dao.read(new Context(), 0L))
        .isNull();
  }

  @Test
  public void test_read_with_max() throws Exception {
    assertThat(this.dao.read(new Context(), Long.MAX_VALUE))
        .isNull();
  }

  @Test
  public void test_read() throws Exception {
    // GIVEN
    final Account author = this.accountTestTool.account();
    log.info("GIVEN - author={}", author);
    final String body = random(current().nextInt(100));
    log.info("GIVEN - body={}", body);
    final Instant createdAt = this.timeProvider.now();
    final Note expected = this.dao.create(new Context(), this.factory.create(new Context(), author, body, createdAt));
    log.info("GIVEN - expected={}", expected);
    final long id = expected.getId();

    this.entityManager.flush();
    this.entityManager.clear();

    // WHEN
    final Note actual = this.dao.read(new Context(), id);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .isEqualTo(expected)
        .isNotSameAs(expected)
        .extracting(Document::getId, Document::getVersion, Document::getOwner,
            Note::getAuthor, Note::getBody,
            Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(id, 0, author,
            author, body, createdAt, createdAt);
  }
}
