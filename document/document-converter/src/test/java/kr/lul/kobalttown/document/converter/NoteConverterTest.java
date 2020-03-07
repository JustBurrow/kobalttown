package kr.lul.kobalttown.document.converter;

import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.dto.AbstractAccountDto;
import kr.lul.kobalttown.account.dto.AccountSimpleDto;
import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.document.dto.AbstractNoteDto;
import kr.lul.kobalttown.document.dto.NoteDetailDto;
import kr.lul.kobalttown.document.dto.NoteSimpleDto;
import kr.lul.kobalttown.document.dto.NoteSummaryDto;
import kr.lul.kobalttown.document.test.NoteTestTool;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/03/07
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NoteConverterTestConfiguration.class)
public class NoteConverterTest {
  protected static final Logger log = getLogger(NoteConverterTest.class);

  @Autowired
  private NoteConverter converter;

  @Autowired
  private NoteTestTool tool;
  @Autowired
  private TimeProvider timeProvider;

  private ZonedDateTime before;

  @Before
  public void setUp() throws Exception {
    this.before = this.timeProvider.zonedDateTime();
    log.info("SETUP - before={}", this.before);
  }

  @Test
  public void test_simple_with_null() throws Exception {
    assertThat(this.converter.simple(null))
        .isNull();
  }

  @Test
  public void test_summary_with_null() throws Exception {
    assertThat(this.converter.summary(null))
        .isNull();
  }

  @Test
  public void test_detail_with_null() throws Exception {
    assertThat(this.converter.detail(null))
        .isNull();
  }

  @Test
  public void test_convert_with_null_target() throws Exception {
    // GIVEN
    final Note note = this.tool.note();
    log.info("GIVEN - note={}", note);

    // WHEN & THEN
    assertThatThrownBy(() -> this.converter.convert(note, null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("targetType is null.");
  }

  @Test
  public void test_convert_to_simple() throws Exception {
    // GIVEN
    final Note note = this.tool.note();
    log.info("GIVEN - note={}", note);
    final long id = note.getId();
    final Account author = note.getAuthor();
    final String body = note.getBody();

    // WHEN
    final NoteSimpleDto dto = this.converter.convert(note, NoteSimpleDto.class);
    log.info("WHEN - dto={}", dto);

    // THEN
    assertThat(dto)
        .isNotNull()
        .extracting(AbstractNoteDto::getId, AbstractNoteDto::getBody)
        .containsSequence(id, body);
    assertThat(dto.getAuthor())
        .isNotNull()
        .extracting(AbstractAccountDto::getId, AbstractAccountDto::getNickname)
        .containsSequence(author.getId(), author.getNickname());
  }

  @Test
  public void test_convert_to_summary() throws Exception {
    // GIVEN
    final Note note = this.tool.note();
    log.info("GIVEN - note={}", note);
    final long id = note.getId();
    final int version = note.getVersion();
    final Account author = note.getAuthor();
    final String body = note.getBody();
    final Instant createdAt = note.getCreatedAt();

    // WHEN
    final NoteSummaryDto dto = this.converter.convert(note, NoteSummaryDto.class);
    log.info("WHEN - dto={}", dto);

    // THEN
    assertThat(dto)
        .isNotNull()
        .extracting(AbstractNoteDto::getId, NoteSummaryDto::getVersion, AbstractNoteDto::getAuthor, AbstractNoteDto::getBody,
            NoteSummaryDto::getCreatedAt)
        .containsSequence(id, version, new AccountSimpleDto(author.getId(), author.getNickname()), body,
            this.timeProvider.zonedDateTime(createdAt));
  }

  @Test
  public void test_convert_to_detail() throws Exception {
    // GIVEN
    final Note note = this.tool.note();
    log.info("GIVEN - note={}", note);
    final long id = note.getId();
    final int version = note.getVersion();
    final Account author = note.getAuthor();
    final String body = note.getBody();
    final Instant createdAt = note.getCreatedAt();
    final Instant updatedAt = note.getUpdatedAt();

    // WHEN
    final NoteDetailDto dto = this.converter.convert(note, NoteDetailDto.class);
    log.info("WHEN - dto={}", dto);

    // THEN
    assertThat(dto)
        .isNotNull()
        .extracting(AbstractNoteDto::getId, NoteDetailDto::getVersion, AbstractNoteDto::getAuthor, AbstractNoteDto::getBody,
            NoteDetailDto::getCreatedAt, NoteDetailDto::getUpdatedAt)
        .containsSequence(id, version, new AccountSimpleDto(author.getId(), author.getNickname()), body,
            this.timeProvider.zonedDateTime(createdAt), this.timeProvider.zonedDateTime(updatedAt));
  }
}
