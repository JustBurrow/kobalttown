package kr.lul.kobalttown.document.service;

import kr.lul.common.data.Context;
import kr.lul.common.data.Creatable;
import kr.lul.common.data.Updatable;
import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.test.AccountTestTool;
import kr.lul.kobalttown.document.data.entity.NoteSnapshotEntity;
import kr.lul.kobalttown.document.data.repository.NoteSnapshotRepository;
import kr.lul.kobalttown.document.domain.Document;
import kr.lul.kobalttown.document.domain.History;
import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.document.domain.NoteSnapshot;
import kr.lul.kobalttown.document.service.params.CreateNoteParams;
import kr.lul.kobalttown.document.service.params.ReadNoteParams;
import kr.lul.kobalttown.document.service.params.UpdateNoteParams;
import kr.lul.kobalttown.document.test.NoteTestTool;
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
import java.util.List;

import static kr.lul.kobalttown.document.domain.NoteUtil.body;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/02/11
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DocumentServiceTestConfiguration.class)
@Transactional
public class NoteServiceTest {
  protected static final Logger log = getLogger(NoteServiceTest.class);

  @Autowired
  private NoteService service;

  @Autowired
  private NoteTestTool tool;
  @Autowired
  private NoteSnapshotRepository snapshotRepository;
  @Autowired
  private AccountTestTool accountTestTool;
  @Autowired
  private EntityManager entityManager;
  @Autowired
  private TimeProvider timeProvider;

  private Account account;
  private Instant instant;

  @Before
  public void setUp() throws Exception {
    this.account = this.accountTestTool.account();
    log.info("SETUP - account={}", this.account);
    this.instant = this.timeProvider.now();
    log.info("SETUP - instant={}", this.instant);
  }

  @Test
  public void test_create_with_null() throws Exception {
    assertThatThrownBy(() -> this.service.create(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("params is null.");
  }

  @Test
  public void test_create() throws Exception {
    // GIVEN
    final String body = body();
    log.info("GIVEN - body={}", body);
    final Instant timestamp = this.timeProvider.now();
    log.info("GIVEN - timestamp={}", timestamp);

    final CreateNoteParams params = new CreateNoteParams(new Context(), this.account, body, timestamp);
    log.info("GIVEN - params={}", params);

    // WHEN
    final Note note = this.service.create(params);
    log.info("WHEN - note={}", note);

    // THEN
    assertThat(note)
        .isNotNull()
        .extracting(Note::getVersion, Note::getOwner, Note::getAuthor, Note::getBody)
        .containsSequence(0, this.account, this.account, body);
    assertThat(note.getId())
        .isPositive();
    assertThat(note.getCreatedAt())
        .isNotNull()
        .isEqualTo(timestamp)
        .isEqualTo(note.getUpdatedAt())
        .isAfterOrEqualTo(this.instant);

    final List<NoteSnapshotEntity> history = this.snapshotRepository.findAllByNote(note);
    assertThat(history)
        .hasSize(1);
    assertThat(history.get(0))
        .isNotNull()
        .extracting(NoteSnapshotEntity::getId, NoteSnapshotEntity::getNote, NoteSnapshotEntity::getBody,
            NoteSnapshotEntity::getCreatedAt)
        .containsSequence(new NoteSnapshotEntity.NoteSnapshotId(note.getId(), 0), note, body, timestamp);
  }

  @Test
  public void test_read_with_null() throws Exception {
    assertThatThrownBy(() -> this.service.read(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("params is null.");
  }

  @Test
  public void test_read_with_negative1_id() throws Exception {
    // GIVEN
    final ReadNoteParams params = new ReadNoteParams(new Context(), this.account, -1L, this.timeProvider.now());
    log.info("GIVEN - params={}", params);

    // WHEN & THEN
    assertThat(this.service.read(params))
        .isNull();
  }

  @Test
  public void test_read_with_0_id() throws Exception {
    // GIVEN
    final ReadNoteParams params = new ReadNoteParams(new Context(), this.account, 0L, this.timeProvider.now());
    log.info("GIVEN - params={}", params);

    // WHEN & THEN
    assertThat(this.service.read(params))
        .isNull();
  }

  @Test
  public void test_read_with_not_exist_id() throws Exception {
    // GIVEN
    final ReadNoteParams params = new ReadNoteParams(new Context(), this.account, Long.MAX_VALUE, this.timeProvider.now());
    log.info("GIVEN - params={}", params);

    // WHEN & THEN
    assertThat(this.service.read(params))
        .isNull();
  }

  @Test
  public void test_read() throws Exception {
    // GIVEN
    final Note note = this.tool.note();
    log.info("GIVEN - note={}", note);
    final long id = note.getId();
    final Account author = note.getAuthor();
    final String body = note.getBody();
    final Instant createdAt = note.getCreatedAt();
    final Instant updatedAt = note.getUpdatedAt();

    this.entityManager.clear();

    final ReadNoteParams params = new ReadNoteParams(new Context(), this.account, id, this.timeProvider.now());
    log.info("GIVEN - params={}", params);

    // WHEN
    final Note actual = this.service.read(params);
    log.info("GIVEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .isEqualTo(note)
        .isNotSameAs(note)
        .extracting(Document::getId, Document::getVersion, Note::getOwner,
            Note::getAuthor, Note::getBody, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(id, 0, author,
            author, body, createdAt, updatedAt);

    final History<NoteSnapshot> history = note.history(Integer.MAX_VALUE, 0);
    assertThat(history)
        .isNotNull()
        .extracting(History::page, History::size, History::totalSize, History::totalPage)
        .containsSequence(0, 1, 1L, 1L);

    final List<NoteSnapshot> content = history.content();
    assertThat(content)
        .isNotNull()
        .hasSize(1);
    assertThat(content.get(0))
        .isNotNull()
        .extracting(NoteSnapshot::getId, NoteSnapshot::getNote, NoteSnapshot::getBody, NoteSnapshot::getCreatedAt)
        .containsSequence(new NoteSnapshotEntity.NoteSnapshotId(note.getId(), 0), actual, body, createdAt);
  }

  @Test
  public void test_update_with_null() throws Exception {
    assertThatThrownBy(() -> this.service.update(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("params is null.");
  }

  @Test
  public void test_update() throws Exception {
    // GIVEN
    final Note note = this.tool.note();
    log.info("GIVEN - note={}", note);
    final Account author = note.getAuthor();
    final String body = note.getBody();
    final Instant createdAt = note.getCreatedAt();

    final String newBody = random(body.length() + 1);
    log.info("GIVEN - newBody={}", newBody);

    final Instant timestamp = this.timeProvider.now();
    final UpdateNoteParams params = new UpdateNoteParams(new Context(), author, note.getId(), newBody, timestamp);
    log.info("GIVEN - params={}", params);

    // WHEN
    final Note updated = this.service.update(params);
    log.info("WHEN - updated={}", updated);

    // THEN
    assertThat(updated)
        .isNotNull()
        .extracting(Document::getVersion, Note::getBody, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(1, newBody, createdAt, timestamp);

    final History<NoteSnapshot> history = updated.history(Integer.MAX_VALUE, 0);
    final List<NoteSnapshotEntity> snapshots = this.snapshotRepository.findAllByNote(note);

    assertThat(history)
        .isNotNull();
    assertThat(history.content())
        .hasSize(2)
        .containsOnly(snapshots.toArray(new NoteSnapshot[2]));
  }

  @Test
  public void test_update_with_non_author() throws Exception {
    // GIVEN
    final Note note = this.tool.note();
    log.info("GIVEN - note={}", note);

    final UpdateNoteParams params = new UpdateNoteParams(new Context(), this.account, note.getId(),
        note.getBody() + "_update_test", this.timeProvider.now());
    log.info("GIVEN - params={}", params);

    // WHEN & THEN
    assertThatThrownBy(() -> this.service.update(params))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("user has no update permission");
  }
}
