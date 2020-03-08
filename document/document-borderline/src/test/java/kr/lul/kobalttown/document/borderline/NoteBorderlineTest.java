package kr.lul.kobalttown.document.borderline;

import kr.lul.common.data.Context;
import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.dto.AccountSimpleDto;
import kr.lul.kobalttown.account.test.AccountTestTool;
import kr.lul.kobalttown.document.borderline.command.CreateNoteCmd;
import kr.lul.kobalttown.document.borderline.command.ReadNoteCmd;
import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.document.dto.AbstractNoteDto;
import kr.lul.kobalttown.document.dto.NoteDetailDto;
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

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/03/07
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DocumentBorderlineTestConfiguration.class)
public class NoteBorderlineTest {
  protected static final Logger log = getLogger(NoteBorderlineTest.class);

  @Autowired
  private NoteBorderline borderline;

  @Autowired
  private NoteTestTool tool;
  @Autowired
  private AccountTestTool accountTestTool;
  @Autowired
  private TimeProvider timeProvider;

  private ZonedDateTime before;

  @Before
  public void setUp() throws Exception {
    this.before = this.timeProvider.zonedDateTime();
    log.info("SETUP - before={}", this.before);
  }

  @Test
  public void test_create_with_null() throws Exception {
    assertThatThrownBy(() -> this.borderline.create(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("cmd is null.");
  }

  @Test
  public void test_create() throws Exception {
    // GIVEN
    final Account author = this.accountTestTool.account();
    log.info("GIVEN - author={}", author);
    final String body = "test note : " + random(current().nextInt(5, 10));
    log.info("GIVEN - body={}", body);
    final Instant timestamp = this.timeProvider.now();
    final CreateNoteCmd cmd = new CreateNoteCmd(new Context(), author.getId(), body, timestamp);
    log.info("GIVEN - cmd={}", cmd);

    // WHEN
    final NoteDetailDto note = this.borderline.create(cmd);
    log.info("WHEN - note={}", note);

    // THEN
    assertThat(note)
        .isNotNull()
        .extracting(NoteDetailDto::getVersion, AbstractNoteDto::getAuthor, AbstractNoteDto::getBody,
            NoteDetailDto::getCreatedAt, NoteDetailDto::getUpdatedAt)
        .containsSequence(0, new AccountSimpleDto(author.getId(), author.getNickname()), body,
            this.timeProvider.zonedDateTime(timestamp), this.timeProvider.zonedDateTime(timestamp));
    assertThat(note.getId())
        .isPositive();
  }

  @Test
  public void test_read_with_null() throws Exception {
    assertThatThrownBy(() -> this.borderline.read(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("cmd is null.");
  }

  @Test
  public void test_read() throws Exception {
    // GIVEN
    final Account user = this.accountTestTool.account();
    log.info("GIVEN - user={}", user);
    final Note note = this.tool.note();
    log.info("GIVEN - note={}", note);
    final Instant timestamp = this.timeProvider.now();
    log.info("GIVEN - timestamp={}", timestamp);

    final ReadNoteCmd cmd = new ReadNoteCmd(new Context(), user.getId(), note.getId(), timestamp);
    log.info("GIVEN - cmd={}", cmd);

    // WHEN
    final NoteDetailDto dto = this.borderline.read(cmd);
    log.info("WHEN - dto={}", dto);

    // THEN
    assertThat(dto)
        .isNotNull()
        .extracting(AbstractNoteDto::getId, NoteDetailDto::getVersion, AbstractNoteDto::getAuthor,
            AbstractNoteDto::getBody, NoteDetailDto::getCreatedAt, NoteDetailDto::getUpdatedAt)
        .containsSequence(note.getId(), 0, new AccountSimpleDto(note.getAuthor().getId(), note.getAuthor().getNickname()),
            note.getBody(), this.timeProvider.zonedDateTime(note.getCreatedAt()), this.timeProvider.zonedDateTime(note.getUpdatedAt()));
  }
}
