package kr.lul.kobalttown.account.web.controller;

import kr.lul.kobalttown.account.borderline.AccountBorderline;
import kr.lul.kobalttown.account.web.AccountWebTestTestConfiguration;
import kr.lul.kobalttown.page.account.AccountMvc.C;
import kr.lul.kobalttown.page.account.AccountMvc.M;
import kr.lul.kobalttown.page.account.AccountMvc.V;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AccountController.class)
@ContextConfiguration(classes = AccountWebTestTestConfiguration.class)
public class AccountControllerWebTest {
  private static final Logger log = getLogger(AccountControllerWebTest.class);

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
  public void test_createForm() throws Exception {
    // WHEN
    this.mock.perform(get(C.CREATE_FORM))
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
        // TODO model attribute check.
        //.andExpect(model().attributeExists(M.ACCOUNT))
        .andDo(print());
  }

  @Test
  public void test_activate_without_token() throws Exception {
    this.mock.perform(get(C.ACTIVATE, ""))
        .andExpect(status().isNotFound())
        .andDo(print());
  }

  @Test
  public void test_activate() throws Exception {
    // GIVEN
    String token = RandomStringUtils.randomAlphanumeric(10);
    log.info("GIVEN - token={}", token);

    // WHEN
    this.mock.perform(get(C.ACTIVATE, token))

        // THEN
        .andExpect(status().isOk())
        .andExpect(view().name(V.ACTIVATE))
        .andDo(print());
  }
}
