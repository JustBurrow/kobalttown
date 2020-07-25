package kr.lul.kobalttown.document.web.controller;

import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.dto.AccountSimpleDto;
import kr.lul.kobalttown.account.test.AccountTestTool;
import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.document.dto.AbstractNoteDto;
import kr.lul.kobalttown.document.dto.NoteDetailDto;
import kr.lul.kobalttown.document.test.NoteTestTool;
import kr.lul.kobalttown.document.web.DocumentWebTestConfiguration;
import kr.lul.kobalttown.document.web.controller.request.CreateNoteReq;
import kr.lul.kobalttown.document.web.controller.request.ReadNoteReq;
import kr.lul.kobalttown.page.note.NoteMvc.C;
import kr.lul.kobalttown.page.note.NoteMvc.M;
import kr.lul.kobalttown.page.note.NoteMvc.V;
import kr.lul.support.spring.security.userdetails.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/03/08
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DocumentWebTestConfiguration.class)
public class NoteControllerTest {
  protected static final Logger log = getLogger(NoteControllerTest.class);

  @Autowired
  private NoteController controller;

  @Autowired
  private NoteTestTool tool;
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
  public void test_createForm_with_null_user() throws Exception {
    assertThatThrownBy(() -> this.controller.createForm(null, new ExtendedModelMap()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("user is null.");
  }

  @Test
  public void test_createForm_with_null_model() throws Exception {
    // GIVEN
    final User user = new User(1L, "testuser", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));

    // WHEN & THEN
    assertThatThrownBy(() -> this.controller.createForm(user, null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("model is null.");
  }

  @Test
  public void test_createForm() throws Exception {
    // GIVEN
    final Account account = this.accountTestTool.account();
    log.info("GIVEN - account={}", account);
    final User user = new User(account.getId(), account.getNickname(), "password",
        List.of(new SimpleGrantedAuthority("ROLE_USER")));
    log.info("GIVEN - user={}", user);
    final Model model = new ExtendedModelMap();
    log.info("GIVEN - model={}", model);

    // WHEN
    final String viewname = this.controller.createForm(user, model);
    log.info("WHEN - viewname={}", viewname);

    // THEN
    assertThat(viewname)
        .isNotNull()
        .isEqualTo(V.CREATE);
    assertThat(model.asMap())
        .hasSize(1)
        .containsKey(M.CREATE_REQ);
  }

  @Test
  public void test_create_with_null_user() throws Exception {
    assertThatThrownBy(
        () -> this.controller.create(null,
            new CreateNoteReq(), new MapBindingResult(new HashMap<>(), M.CREATE_REQ),
            new ExtendedModelMap()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("user is null.");
  }

  @Test
  public void test_create_with_null_req() throws Exception {
    assertThatThrownBy(
        () -> this.controller.create(new User(1L, "testuser", "password", List.of(new SimpleGrantedAuthority("ROLE_USER"))),
            null, new MapBindingResult(new HashMap<>(), M.CREATE_REQ),
            new ExtendedModelMap()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("req is null.");
  }

  @Test
  public void test_create_with_null_binding() throws Exception {
    assertThatThrownBy(
        () -> this.controller.create(new User(1L, "testuser", "password", List.of(new SimpleGrantedAuthority("ROLE_USER"))),
            new CreateNoteReq("test body"), null,
            new ExtendedModelMap()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("binding is null.");
  }

  @Test
  public void test_create_with_null_model() throws Exception {
    assertThatThrownBy(
        () -> this.controller.create(new User(1L, "testuser", "password", List.of(new SimpleGrantedAuthority("ROLE_USER"))),
            new CreateNoteReq("test body"), new MapBindingResult(new HashMap<>(), M.CREATE_REQ),
            null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("model is null.");
  }

  @Test
  public void test_create() throws Exception {
    // GIVEN
    final Account account = this.accountTestTool.account();
    log.info("GIVEN - account={}", account);
    final User user = new User(account.getId(), account.getNickname(), "password",
        List.of(new SimpleGrantedAuthority("ROLE_USER")));
    log.info("GIVEN - user={}", user);
    final String body = "test note #" + random(current().nextInt(10, 20));
    log.info("GIVEN - body={}", body);

    final CreateNoteReq req = new CreateNoteReq(body);
    log.info("GIVEN - req={}", req);
    final BindingResult binding = new MapBindingResult(new HashMap<>(), M.CREATE_REQ);
    log.info("GIVEN - binding={}", binding);
    final Model model = new ExtendedModelMap();
    log.info("GIVEN - model={}", model);

    // WHEN
    final String viewname = this.controller.create(user, req, binding, model);
    log.info("WHEN - viewname={}", viewname);

    // THEN
    assertThat(viewname)
        .isNotNull()
        .startsWith("redirect:" + C.GROUP);
    assertThat(binding)
        .extracting(BindingResult::hasErrors)
        .isEqualTo(false);
    assertThat(model.asMap())
        .isEmpty();
  }

  @Test
  public void test_detail() throws Exception {
    // GIVEN
    final Note note = this.tool.note();
    final Account author = note.getAuthor();
    log.info("GIVEN - note={}", note);

    final Account account = this.accountTestTool.account();
    log.info("GIVEN - account={}", account);
    final User user = new User(account.getId(), account.getNickname(), "password",
        List.of(new SimpleGrantedAuthority("ROLE_USER")));
    log.info("GIVEN - user={}", user);
    final Model model = new ExtendedModelMap();

    // WHEN
    final String template = this.controller.detail(user, note.getId(), new ReadNoteReq(), model);
    log.info("WHEN - template={}", template);

    // THEN
    assertThat(template)
        .isNotNull()
        .isEqualTo(V.DETAIL);
    assertThat(model.asMap())
        .isNotEmpty();
    assertThat((NoteDetailDto) model.getAttribute(M.NOTE))
        .isNotNull()
        .extracting(NoteDetailDto::getId, NoteDetailDto::getVersion, AbstractNoteDto::getAuthor, AbstractNoteDto::getBody,
            NoteDetailDto::getCreatedAt, NoteDetailDto::getUpdatedAt)
        .containsSequence(note.getId(), 0, new AccountSimpleDto(author.getId(), author.getNickname()), note.getBody(),
            this.timeProvider.zonedDateTime(note.getCreatedAt()), this.timeProvider.zonedDateTime(note.getUpdatedAt()));
  }
}
