package kr.lul.kobalttown.account.web.controller;

import kr.lul.kobalttown.account.borderline.AccountBorderline;
import kr.lul.kobalttown.account.web.AccountWebTestConfiguration;
import kr.lul.kobalttown.configuration.security.WebSecurityConfiguration;
import kr.lul.kobalttown.configuration.web.WebMvcConfiguration;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static kr.lul.kobalttown.page.account.AccountMvc.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
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

  @Before
  public void setUp() throws Exception {
    assertThat(this.mock).isNotNull();
    assertThat(this.borderline).isNotNull();
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
        .param("nickname", "nickname")
        .param("password", "password")
        .with(anonymous())
    )

        // THEN
        .andExpect(status().isOk())
        .andExpect(view().name(V.CREATE_FORM))
        .andExpect(model().attributeExists(M.CREATE_REQ))
        .andExpect(model().attributeHasErrors(M.CREATE_REQ))
        .andDo(print());
  }

  @Test
  public void test_create_without_password() throws Exception {
    // WHEN
    this.mock.perform(post(C.CREATE)
        .param("nickname", "nickname")
        .param("confirm", "confirm")
        .with(anonymous())
    )

        // THEN
        .andExpect(status().isOk())
        .andExpect(view().name(V.CREATE_FORM))
        .andExpect(model().attributeExists(M.CREATE_REQ))
        .andExpect(model().attributeHasErrors(M.CREATE_REQ))
        .andDo(print());
  }

  @Test
  public void test_create_with_not_match_confirm() throws Exception {
    // WHEN
    this.mock.perform(post(C.CREATE)
        .param("nickname", "nickname")
        .param("password", "password")
        .param("confirm", "confirm")
        .with(anonymous())
    )

        // THEN
        .andExpect(status().isOk())
        .andExpect(view().name(V.CREATE_FORM))
        .andExpect(model().attributeExists(M.CREATE_REQ))
        .andExpect(model().attributeHasErrors(M.CREATE_REQ))
        .andDo(print());
  }

  @Test
  public void test_create() throws Exception {
    // TODO borderline 메서드 추가.

    // WHEN
    this.mock.perform(post(C.CREATE)
        .param("nickname", "nickname")
        .param("email", "just.burrow@lul.kr")
        .param("password", "password")
        .param("confirm", "password")
        .with(anonymous())
    )

        // THEN
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrlPattern(C.GROUP + "/*"))
        .andDo(print());
  }

  @Test
  public void test_list() throws Exception {
    // WHEN
    this.mock.perform(get(C.LIST))

        // THEN
        .andExpect(status().isOk())
        .andExpect(handler().handlerType(AccountControllerImpl.class))
        .andExpect(view().name(V.LIST))
        .andExpect(model().hasNoErrors())
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
    this.mock.perform(get(C.DETAIL, 1L))
        .andExpect(status().isOk())
        .andExpect(view().name(V.DETAIL))
        .andExpect(model().attributeExists(M.ACCOUNT))
        .andDo(print());
  }

  @Test
  public void test_activate_without_token() throws Exception {
    this.mock.perform(get(C.ACTIVATE, "")
        .with(anonymous())
    )
        .andExpect(status().isNotFound())
        .andDo(print());
  }

  @Test
  public void test_activate() throws Exception {
    // GIVEN
    String token = RandomStringUtils.randomAlphanumeric(10);
    log.info("GIVEN - token={}", token);

    // WHEN
    this.mock.perform(get(C.ACTIVATE, token)
        .with(anonymous())
    )

        // THEN
        .andExpect(status().isOk())
        .andExpect(view().name(V.ACTIVATE))
        .andDo(print());
  }
}
