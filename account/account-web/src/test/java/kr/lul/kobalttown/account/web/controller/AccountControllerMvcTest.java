package kr.lul.kobalttown.account.web.controller;

import kr.lul.common.data.Context;
import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.borderline.AccountBorderline;
import kr.lul.kobalttown.account.dto.AccountDetailDto;
import kr.lul.kobalttown.account.web.AccountWebTestConfiguration;
import kr.lul.kobalttown.configuration.security.UserDetailsService;
import kr.lul.kobalttown.configuration.security.WebSecurityConfiguration;
import kr.lul.kobalttown.configuration.web.WebMvcConfiguration;
import kr.lul.kobalttown.page.root.GlobalMvc;
import kr.lul.support.spring.security.userdetails.User;
import kr.lul.support.spring.web.context.ContextService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.List;

import static java.util.concurrent.ThreadLocalRandom.current;
import static kr.lul.kobalttown.account.domain.AccountUtil.nickname;
import static kr.lul.kobalttown.account.domain.CredentialUtil.email;
import static kr.lul.kobalttown.account.domain.CredentialUtil.userKey;
import static kr.lul.kobalttown.account.domain.EnableCodeUtil.token;
import static kr.lul.kobalttown.page.account.AccountMvc.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
  @MockBean
  private UserDetailsService userDetailsService;

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
    this.mock.perform(
        post(C.CREATE)
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
  public void test_validate_without_code() throws Exception {
    this.mock.perform(get(C.GROUP + "/validate"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void test_validate_with_short_code() throws Exception {
    // GIVEN
    final String code = token().substring(1);
    log.info("GIVEN - code={}", code);

    // WHEN
    this.mock.perform(get(C.GROUP + "/enable/" + code)
                          .with(anonymous()))

        // THEN
        .andExpect(status().isNotFound())
        .andExpect(view().name(GlobalMvc.V.ERROR_404))
        .andDo(print());

    verify(this.borderline, never()).enable(any());
  }

  @Test
  public void test_validate_with_long_code() throws Exception {
    // GIVEN
    final String code = token() + "a";
    log.info("GIVEN - code={}", code);

    // WHEN
    this.mock.perform(get(C.GROUP + "/enable/" + code)
                          .with(anonymous()))

        // THEN
        .andExpect(status().isNotFound())
        .andExpect(view().name(GlobalMvc.V.ERROR_404))
        .andDo(print());

    verify(this.borderline, never()).enable(any());
  }

  @Test
  public void test_VALIDATE_ISSUE_FORM_with_authenticated() throws Exception {
    // WHEN
    this.mock.perform(get(C.ISSUE_ENABLE_CODE_FORM)
                          .with(user("nickname #" + current().nextInt(Integer.MAX_VALUE))))

        // THEN
        .andExpect(status().is4xxClientError())
        .andDo(print());
  }

  @Test
  public void test_ENABLE_CODE_ISSUE_form() throws Exception {
    // WHEN
    this.mock.perform(get(C.ISSUE_ENABLE_CODE_FORM)
                          .with(anonymous()))

        // THEN
        .andExpect(status().isOk())
        .andExpect(view().name(V.ISSUE_ENABLE_CODE))
        .andExpect(model().attributeExists(M.ISSUE_ENABLE_CODE))
        .andDo(print());
  }

  @Test
  public void test_detail_with_non_digits() throws Exception {
    // WHEN
    this.mock.perform(get(C.PROFILE, "non-digit"))
        .andExpect(status().isNotFound())
        .andDo(print());
  }

  @Test
  public void test_detail_with_leading_0() throws Exception {
    this.mock.perform(get(C.PROFILE, "01"))
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
    this.mock.perform(get(C.PROFILE, 1L)
                          .with(user(user)))

        // THEN
        .andExpect(status().isOk())
        .andExpect(view().name(V.DETAIL))
        .andExpect(model().attribute(M.ACCOUNT, dto))
        .andDo(print());
  }

  @Test
  public void test_setting() throws Exception {
    // GIVEN
    final User user = new User(1L, nickname(), "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
    log.info("GIVEN - user={}", user);
    final AccountDetailDto dto = new AccountDetailDto(1L, "nickname", true, this.before, this.before);
    log.info("GIVEN - dto={}", dto);

    // WHEN
    this.mock.perform(get(C.SETTING)
                          .with(user(user)))

        // THEN
        .andExpect(status().isOk())
        .andExpect(view().name(V.SETTING))
        .andDo(print());
  }

  @Test
  public void test_passwordForm() throws Exception {
    // GIVEN
    final User user = new User(1L, nickname(), "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
    log.info("GIVEN - user={}", user);

    // WHEN
    this.mock.perform(get(C.PASSWORD_FORM)
                          .with(user(user)))

        // THEN
        .andExpect(status().isOk())
        .andExpect(view().name(V.PASSWORD))
        .andExpect(model().attributeExists(M.UPDATE_PASSWORD_REQ))
        .andDo(print());
  }

  @Test
  @Ignore
  public void test_password_without_current() throws Exception {
    // GIVEN
    final User user = new User(1L, nickname(), "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
    log.info("GIVEN - user={}", user);

    // WHEN
    this.mock.perform(patch(C.PASSWORD)
                          .param("password", "password2")
                          .param("confirm", "password2")
                          .with(user(user)))

        // THEN
        .andExpect(status().isOk())
        .andExpect(view().name(V.PASSWORD))
        .andDo(print());
  }

  @Test
  @Ignore
  public void test_password_without_password() throws Exception {
    // GIVEN
    final User user = new User(1L, nickname(), "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
    log.info("GIVEN - user={}", user);

    // WHEN
    this.mock.perform(patch(C.PASSWORD)
                          .param(M.UPDATE_PASSWORD_REQ + ".current", "password")
                          .param(M.UPDATE_PASSWORD_REQ + ".confirm", "password2")
                          .with(user(user)))

        // THEN
        .andExpect(status().isOk())
        .andExpect(view().name(V.PASSWORD))
        .andDo(print());
  }

  @Test
  @Ignore
  public void test_password_without_confirm() throws Exception {
    // GIVEN
    final User user = new User(1L, nickname(), "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
    log.info("GIVEN - user={}", user);

    // WHEN
    this.mock.perform(patch(C.PASSWORD)
                          .param("current", "password")
                          .param("password", "password2")
                          .with(user(user)))

        // THEN
        .andExpect(status().isOk())
        .andExpect(view().name(V.PASSWORD))
        .andDo(print());
  }

  @Test
  @Ignore
  public void test_password_without_confirm_not_match() throws Exception {
    // GIVEN
    final User user = new User(1L, nickname(), "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
    log.info("GIVEN - user={}", user);

    // WHEN
    this.mock.perform(patch(C.PASSWORD)
                          .param("current", "password")
                          .param("password", "password2")
                          .param("confirm", "password3")
                          .with(user(user)))

        // THEN
        .andExpect(status().isOk())
        .andExpect(view().name(V.PASSWORD))
        .andDo(print());
  }

  @Test
  @WithUserDetails
  public void test_password() throws Exception {
    // GIVEN
    final User user = new User(1L, "user", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
    log.info("GIVEN - user={}", user);
    when(this.userDetailsService.loadUserByUsername("user"))
        .thenReturn(user);

    // WHEN
    this.mock.perform(patch(C.PASSWORD)
                          .param("current", "password")
                          .param("password", "password2")
                          .param("confirm", "password2")
                          .with(user(user)))

        // THEN
        .andExpect(status().isOk())
        .andExpect(view().name(V.PASSWORD_UPDATED))
        .andDo(print());
  }
}
