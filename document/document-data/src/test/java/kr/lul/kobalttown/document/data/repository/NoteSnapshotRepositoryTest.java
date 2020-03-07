package kr.lul.kobalttown.document.data.repository;

import kr.lul.common.data.Context;
import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.test.AccountTestTool;
import kr.lul.kobalttown.document.data.DocumentDataTestConfiguration;
import kr.lul.kobalttown.document.data.entity.NoteEntity;
import kr.lul.kobalttown.document.data.entity.NoteSnapshotEntity;
import kr.lul.kobalttown.document.data.factory.NoteFactory;
import kr.lul.kobalttown.document.domain.Note;
import org.junit.Before;
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
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/03/06
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DocumentDataTestConfiguration.class)
@Transactional
public class NoteSnapshotRepositoryTest {
  protected static final Logger log = getLogger(NoteSnapshotRepositoryTest.class);

  @Autowired
  private NoteSnapshotRepository repository;

  @Autowired
  private NoteFactory factory;
  @Autowired
  private NoteRepository noteRepository;
  @Autowired
  private AccountTestTool accountTestTool;
  @Autowired
  private EntityManager entityManager;
  @Autowired
  private TimeProvider timeProvider;

  private Instant before;

  @Before
  public void setUp() throws Exception {
    this.before = this.timeProvider.now();
    log.info("SETUP - before={}", this.before);
  }

  @Test
  public void test_findById() throws Exception {
    // GIVEN
    final Account account = this.accountTestTool.account();
    log.info("GIVEN - account={}", account);

    final Context context = new Context();
    final String body = "test body : " + random(current().nextInt(5, 20));
    final Instant createdAt = this.timeProvider.now();
    log.info("GIVEN - context={}, body='{}', createdAt={}", context, body, createdAt);

    final Note note = this.noteRepository.saveAndFlush((NoteEntity) this.factory.create(context, account, body, createdAt));
    final long noteId = note.getId();
    log.info("GIVEN - note={}", note);

    this.entityManager.clear();

    final NoteSnapshotEntity.NoteSnapshotId id = new NoteSnapshotEntity.NoteSnapshotId(noteId, 0);
    log.info("GIVEN - id={}", id);

    // WHEN
    final NoteSnapshotEntity snapshot = this.repository.findById(id).get();
    log.info("WHEN - snapshot={}", snapshot);

    // THEN
    assertThat(snapshot)
        .isNotNull()
        .extracting(NoteSnapshotEntity::getNote, NoteSnapshotEntity::getBody, NoteSnapshotEntity::getCreatedAt)
        .containsSequence(note, body, createdAt);
  }
}
