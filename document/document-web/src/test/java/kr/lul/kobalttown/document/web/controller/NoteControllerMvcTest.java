package kr.lul.kobalttown.document.web.controller;

import kr.lul.common.data.Context;
import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.dto.AccountSimpleDto;
import kr.lul.kobalttown.configuration.security.WebSecurityConfiguration;
import kr.lul.kobalttown.configuration.web.WebMvcConfiguration;
import kr.lul.kobalttown.document.borderline.NoteBorderline;
import kr.lul.kobalttown.document.dto.NoteDetailDto;
import kr.lul.kobalttown.document.web.DocumentWebTestConfiguration;
import kr.lul.kobalttown.page.note.NoteMvc.C;
import kr.lul.kobalttown.page.note.NoteMvc.M;
import kr.lul.kobalttown.page.note.NoteMvc.V;
import kr.lul.support.spring.security.userdetails.User;
import kr.lul.support.spring.web.context.ContextService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author justburrow
 * @since 2020/03/09
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = NoteControllerImpl.class)
@ContextConfiguration(classes = DocumentWebTestConfiguration.class)
@Import({WebMvcConfiguration.class, WebSecurityConfiguration.class})
public class NoteControllerMvcTest {
  protected static final Logger log = getLogger(NoteControllerMvcTest.class);

  @Autowired
  private MockMvc mock;
  @MockBean
  private NoteBorderline noteBorderline;

  @Autowired
  private ContextService contextService;
  @Autowired
  private TimeProvider timeProvider;

  private Context context;
  private ZonedDateTime before;

  @Before
  public void setUp() throws Exception {
    this.context = this.contextService.issue();
    log.info("SETUP - context={}", this.context);
    this.before = this.timeProvider.zonedDateTime();
    log.info("SETUP - before={}", this.before);
  }

  @Test
  public void test_createForm_with_anonymous() throws Exception {
    this.mock.perform(
        get(C.CREATE_FORM)
            .with(anonymous()))
        .andExpect(status().is3xxRedirection())
        .andDo(print());
  }

  @Test
  public void test_createForm() throws Exception {
    // GIVEN
    final User user = new User(1L, "nickname", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
    log.info("GIVEN - user={}", user);

    // WHEN & THEN
    this.mock.perform(
        get(C.CREATE_FORM)
            .with(user(user)))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists(M.CREATE_REQ))
        .andExpect(view().name(V.CREATE))
        .andDo(print());
  }

  @Test
  public void test_create_with_anonymous() throws Exception {
    this.mock.perform(
        post(C.CREATE)
            .param("body", "test body")
            .with(csrf())
            .with(anonymous()))
        .andExpect(status().is3xxRedirection())
        .andDo(print());
  }

  @Test
  public void test_create_without_csrf() throws Exception {
    // GIVEN
    final User user = new User(1L, "nickname", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
    log.info("GIVEN - user={}", user);
    final long id = 123L;

    when(this.noteBorderline.create(any()))
        .thenReturn(new NoteDetailDto(id, 0, new AccountSimpleDto(1L, "nickname"), "test body", this.before, this.before));

    // WHEN
    this.mock.perform(
        post(C.CREATE)
            .param("body", "test body")
            .with(user(user)))

        // THEN
        .andExpect(status().is4xxClientError())
        .andDo(print());
  }

  @Test
  public void test_create() throws Exception {
    // GIVEN
    final User user = new User(1L, "nickname", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
    log.info("GIVEN - user={}", user);
    final long id = 123L;

    when(this.noteBorderline.create(any()))
        .thenReturn(new NoteDetailDto(id, 0, new AccountSimpleDto(1L, "nickname"), "test body", this.before, this.before));

    // WHEN
    this.mock.perform(
        post(C.CREATE)
            .param("body", "test body")
            .with(csrf())
            .with(user(user)))
        // THEN
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(C.GROUP + "/" + id))
        .andDo(print());
  }
}
