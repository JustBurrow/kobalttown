package kr.lul.kobalttown.document.data.repository;

import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.test.AccountTestTool;
import kr.lul.kobalttown.document.data.DocumentDataTestConfiguration;
import kr.lul.kobalttown.document.data.entity.AbstractNoteCommentEntity;
import kr.lul.kobalttown.document.data.entity.NoteCommentEntity;
import kr.lul.kobalttown.document.data.entity.NoteEntity;
import kr.lul.support.spring.data.jpa.entiy.CreatableEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/03/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DocumentDataTestConfiguration.class)
@Transactional
public class NoteCommentRepositoryTest {
  protected static final Logger log = getLogger(NoteCommentRepositoryTest.class);

  @Autowired
  private NoteCommentRepository repository;

  @Autowired
  private NoteRepository noteRepository;
  @Autowired
  private AccountTestTool accountTestTool;
  @Autowired
  private TimeProvider timeProvider;

  private Account account;
  private NoteEntity note;

  private int i;

  @Before
  public void setUp() throws Exception {
    this.account = this.accountTestTool.account();
    log.info("SETUP - account={}", this.account);

    this.note = this.noteRepository
                    .saveAndFlush(new NoteEntity(this.account, "test note #setup" + ++this.i, this.timeProvider.now()));
    log.info("SETUP - note={}", this.note);
  }

  @Test
  public void test_findAll() throws Exception {
    assertThat(this.repository.findAll())
        .isNotNull();
  }

  @Test
  public void test_save() throws Exception {
    // GIVEN
    final String body = random(10);
    final Instant createdAt = this.timeProvider.now();
    log.info("GIVEN - body={}, createdAt={}", body, createdAt);

    final NoteCommentEntity comment = new NoteCommentEntity(this.account, this.note, body, createdAt);
    log.info("GIVEN - comment={}", comment);

    // WHEN
    final NoteCommentEntity actual = this.repository.save(comment);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .extracting(AbstractNoteCommentEntity::getAuthor, NoteCommentEntity::getNote, AbstractNoteCommentEntity::getSnapshot,
            AbstractNoteCommentEntity::getBody, CreatableEntity::getCreatedAt)
        .containsSequence(this.account, this.note, this.note.history(1, 0).content().get(0),
            body, createdAt);
    assertThat(actual.getId())
        .isPositive();
  }
}
