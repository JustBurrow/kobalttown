package kr.lul.kobalttown.document.test;

import kr.lul.common.data.Creatable;
import kr.lul.common.data.Updatable;
import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.test.AccountTestTool;
import kr.lul.kobalttown.document.data.entity.NoteSnapshotEntity.NoteSnapshotId;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static kr.lul.kobalttown.document.domain.Note.BODY_MAX_LENGTH;
import static kr.lul.kobalttown.document.domain.NoteUtil.body;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/02/11
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NoteTestToolTestConfiguration.class)
@Transactional
public class NoteTestToolTest {
  protected static final Logger log = getLogger(NoteTestToolTest.class);

  @Autowired
  private NoteTestTool tool;

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
  public void test_note() throws Exception {
    // WHEN
    final Note note = this.tool.note();
    log.info("WHEN - note={}", note);

    // THEN
    assertThat(note)
        .isNotNull()
        .extracting(Document::getVersion)
        .isEqualTo(0);
    assertThat(note.getId())
        .isPositive();
    assertThat(note.getAuthor())
        .isNotNull()
        .isEqualTo(note.getOwner());
    assertThat(note.getBody())
        .isNotNull()
        .hasSizeLessThanOrEqualTo(BODY_MAX_LENGTH);
    assertThat(note.getCreatedAt())
        .isNotNull()
        .isEqualTo(note.getUpdatedAt())
        .isAfterOrEqualTo(this.instant);

    final History<NoteSnapshot> history = note.history(Integer.MAX_VALUE, 0);
    assertThat(history)
        .isNotNull()
        .extracting(History::size, History::page, History::totalSize, History::totalPage)
        .containsSequence(1, 0, 1L, 1L);
    final List<NoteSnapshot> content = history.content();
    assertThat(content)
        .isNotNull()
        .hasSize(1);
    assertThat(content.get(0))
        .isNotNull()
        .extracting(NoteSnapshot::getId, NoteSnapshot::getNote, NoteSnapshot::getBody, NoteSnapshot::getCreatedAt)
        .containsSequence(new NoteSnapshotId(note.getId(), 0), note, note.getBody(), note.getCreatedAt());
  }

  @Test
  public void test_note_with_author() throws Exception {
    // GIVEN
    final Account author = this.accountTestTool.account();
    log.info("GIVEN - author={}", author);

    // WHEN
    final Note note = this.tool.note(author, null, null);
    log.info("WHEN - note={}", note);

    // THEN
    assertThat(note)
        .isNotNull()
        .extracting(Document::getVersion, Note::getAuthor, Note::getOwner)
        .containsSequence(0, author, author);
    assertThat(note.getId())
        .isPositive();
    assertThat(note.getBody())
        .isNotNull()
        .hasSizeLessThanOrEqualTo(BODY_MAX_LENGTH);
    assertThat(note.getCreatedAt())
        .isNotNull()
        .isEqualTo(note.getUpdatedAt())
        .isAfterOrEqualTo(this.instant);
  }

  @Test
  public void test_note_with_body() throws Exception {
    // GIVEN
    final String body = body();
    log.info("GIVEN - body={}", body);

    // WHEN
    final Note note = this.tool.note(null, body, null);
    log.info("WHEN - note={}", note);

    // THEN
    assertThat(note)
        .isNotNull()
        .extracting(Document::getVersion, Note::getBody)
        .containsSequence(0, body);
    assertThat(note.getId())
        .isPositive();
    assertThat(note.getAuthor())
        .isNotNull()
        .isEqualTo(note.getOwner());
    assertThat(note.getCreatedAt())
        .isNotNull()
        .isEqualTo(note.getUpdatedAt())
        .isAfterOrEqualTo(this.instant);
  }

  @Test
  public void test_note_with_createdAt() throws Exception {
    // GIVEN
    final Instant createdAt = this.timeProvider.now().plusMillis(100L);
    log.info("GIVEN - createdAt={}", createdAt);

    // WHEN
    final Note note = this.tool.note(null, null, createdAt);
    log.info("WHEN - note={}", note);

    // THEN
    assertThat(note)
        .isNotNull()
        .extracting(Document::getVersion, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(0, createdAt, createdAt);
    assertThat(note.getId())
        .isPositive();
    assertThat(note.getAuthor())
        .isNotNull()
        .isEqualTo(note.getOwner());
    assertThat(note.getBody())
        .isNotNull()
        .hasSizeLessThanOrEqualTo(BODY_MAX_LENGTH);
  }

  @Test
  public void test_note_with_args() throws Exception {
    // GIVEN
    final Account author = this.accountTestTool.account();
    log.info("GIVEN - author={}", author);
    final String body = body();
    log.info("GIVEN - body={}", body);
    final Instant createdAt = author.getCreatedAt().plusMillis(100L);
    log.info("GIVEN - createdAt={}", createdAt);

    // WHEN
    final Note note = this.tool.note(author, body, createdAt);
    log.info("WHEN - note={}", note);

    // THEN
    assertThat(note)
        .isNotNull()
        .extracting(Document::getVersion, Note::getOwner, Note::getAuthor, Note::getBody,
            Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(0, author, author, body, createdAt, createdAt);
    assertThat(note.getId())
        .isPositive();
  }
}
