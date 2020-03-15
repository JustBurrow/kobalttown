package kr.lul.kobalttown.document.data.repository;

import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.test.AccountTestTool;
import kr.lul.kobalttown.document.data.DocumentDataTestConfiguration;
import kr.lul.kobalttown.document.data.entity.NoteEntity;
import kr.lul.support.spring.data.jpa.entiy.SavableEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/02/07
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DocumentDataTestConfiguration.class)
@Transactional
public class NoteRepositoryTest {
  protected static final Logger log = getLogger(NoteRepositoryTest.class);

  @Autowired
  private NoteRepository repository;

  @Autowired
  private AccountTestTool accountTestTool;

  @Autowired
  private TimeProvider timeProvider;

  private Instant before;

  @Before
  public void setUp() throws Exception {
    this.before = this.timeProvider.now();
    log.info("SETUP - before={}", this.before);
  }

  @Test
  public void test_count() throws Exception {
    assertThat(this.repository.count())
        .isNotNegative();
  }

  @Test
  public void test_save() throws Exception {
    // GIVEN
    final Account account = this.accountTestTool.account();
    final String body = "test body.";
    final Instant createdAt = this.timeProvider.now();
    final NoteEntity note = new NoteEntity(account, body, createdAt);
    log.info("GIVEN - note={}", note);

    // WHEN
    final NoteEntity saved = this.repository.save(note);
    log.info("WHEN - saved={}", saved);

    // THEN
    assertThat(saved)
        .isNotNull()
        .extracting(NoteEntity::getAuthor, NoteEntity::getBody, SavableEntity::getCreatedAt, SavableEntity::getUpdatedAt)
        .containsSequence(account, body, createdAt, createdAt);
    assertThat(saved.getId())
        .isPositive();
  }
}
