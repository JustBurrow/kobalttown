package kr.lul.kobalttown.account.web.controller;

import kr.lul.common.data.Context;
import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.borderline.AccountBorderline;
import kr.lul.kobalttown.account.dto.AccountDetailDto;
import kr.lul.kobalttown.account.web.AccountWebTestConfiguration;
import kr.lul.kobalttown.configuration.security.WebSecurityConfiguration;
import kr.lul.kobalttown.configuration.web.WebMvcConfiguration;
import kr.lul.support.spring.security.userdetails.User;
import kr.lul.support.spring.web.context.ContextService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.List;

import static kr.lul.kobalttown.account.domain.AccountUtil.nickname;
import static kr.lul.kobalttown.account.domain.CredentialUtil.email;
import static kr.lul.kobalttown.account.domain.CredentialUtil.userKey;
import static kr.lul.kobalttown.page.account.AccountMvc.*;
import static org.assertj.core.api.Assertions.assertThat;
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
 * @since 2019/11/30
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AccountControllerImpl.class)
@ContextConfiguration(classes = AccountWebTestConfiguration.class)
@Import({WebMvcConfiguration.class, WebSecurityConfiguration.class})
public class AccountControllerMvcTest {
  private static final Logger log = getLogger(AccountControllerMvcTest.class);

  @Autowired
  private MockMvc mock;

  @MockBean
  private AccountBorderline borderline;

  @Autowired
  private ContextService contextService;
  @Autowired
  private TimeProvider timeProvider;

  private Context context;
  private ZonedDateTime before;

  @Before
  public void setUp() throws Exception {
    assertThat(this.mock).isNotNull();
    assertThat(this.borderline).isNotNull();
    assertThat(this.contextService).isNotNull();
    assertThat(this.timeProvider).isNotNull();

    this.context = this.contextService.issue();
    log.info("SETUP - context={}", this.context);

    this.before = this.timeProvider.zonedDateTime();
    log.info("SETUP - before={}", this.before);
  }

  @After
  public void tearDown() throws Exception {
    this.contextService.clear();
  }

  @Test
  @WithAnonymousUser
  public void test_createForm() throws Exception {
    // WHEN
    this.mock.perform(get(C.CREATE_FORM)
                          .with(anonymous()))

        // THEN
        .andExpect(status().isOk())
        .andExpect(view().name(V.CREATE_FORM))
        .andExpect(model().attributeExists(M.CREATE_REQ))
        .andDo(print());
  }

  @Test
  public void test_create_without_confirm() throws Exception {
    // WHEN
    this.mock.perform(post(C.CREATE)
                          .param("nickname", nickname())
                          .param("userKey", userKey())
                          .param("password", "password")
                          .with(anonymous())
                          .with(csrf()))

        // THEN
        .andExpect(status().isOk())
        .andExpect(view().name(V.CREATE_FORM))
        .andExpect(model().attributeExists(M.CREATE_REQ))
        .andExpect(model().attributeHasErrors(M.CREATE_REQ))
        .andExpect(model().attributeHasFieldErrors(M.CREATE_REQ, "confirm"))
        .andDo(print());
  }

  @Test
  public void test_create_without_password() throws Exception {
    // WHEN
    this.mock.perform(post(C.CREATE)
                          .param("nickname", nickname())
                          .param("userKey", userKey())
                          .param("confirm", "confirm")
                          .with(anonymous())
                          .with(csrf()))

        // THEN
        .andExpect(status().isOk())
        .andExpect(view().name(V.CREATE_FORM))
        .andExpect(model().attributeExists(M.CREATE_REQ))
        .andExpect(model().attributeHasFieldErrors(M.CREATE_REQ, "password"))
        .andDo(print());
  }

  @Test
  public void test_create_with_not_match_confirm() throws Exception {
    // WHEN
    this.mock.perform(post(C.CREATE)
                          .param("nickname", nickname())
                          .param("userKey", userKey())
                          .param("password", "password")
                          .param("confirm", "confirm")
                          .with(anonymous())
                          .with(csrf()))

        // THEN
        .andExpect(status().isOk())
        .andExpect(view().name(V.CREATE_FORM))
        .andExpect(model().attributeExists(M.CREATE_REQ))
        .andExpect(model().attributeHasFieldErrors(M.CREATE_REQ, "confirm"))
        .andDo(print());
  }

  @Test
  public void test_create() throws Exception {
    // GIVEN
    final AccountDetailDto dto = new AccountDetailDto(1L, "nickname", false, this.before, this.before);
    log.info("GIVEN - dto={}", dto);
    when(this.borderline.create(any()))
        .thenReturn(dto);

    // WHEN
    this.mock.perform(post(C.CREATE)
                          .param("nickname", nickname())
                          .param("email", email())
                          .param("userKey", userKey())
                          .param("password", "password")
                          .param("confirm", "password")
                          .with(anonymous())
                          .with(csrf()))

        // THEN
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/"))
        .andDo(print());
  }

  @Test
  public void test_detail_with_non_digits() throws Exception {
    // WHEN
    this.mock.perform(get(C.DETAIL, "non-digit"))
        .andExpect(status().isNotFound())
        .andDo(print());
  }

  @Test
  public void test_detail_with_leading_0() throws Exception {
    this.mock.perform(get(C.DETAIL, "01"))
        .andExpect(status().isNotFound())
        .andDo(print());
  }

  @Test
  public void test_detail() throws Exception {
    // GIVEN
    final User user = new User(1L, nickname(), "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
    log.info("GIVEN - user={}", user);
    final AccountDetailDto dto = new AccountDetailDto(1L, "nickname", true, this.before, this.before);
    log.info("GIVEN - dto={}", dto);

    when(this.borderline.read(any()))
        .thenReturn(dto);

    // WHEN
    this.mock.perform(get(C.DETAIL, 1L)
                          .with(user(user)))

        // THEN
        .andExpect(status().isOk())
        .andExpect(view().name(V.DETAIL))
        .andExpect(model().attribute(M.ACCOUNT, dto))
        .andDo(print());
  }
}
