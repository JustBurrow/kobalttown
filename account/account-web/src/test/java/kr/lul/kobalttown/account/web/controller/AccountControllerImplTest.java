package kr.lul.kobalttown.account.web.controller;

import kr.lul.kobalttown.account.web.AccountWebTestConfiguration;
import kr.lul.kobalttown.account.web.controller.request.CreateAccountReq;
import kr.lul.kobalttown.page.account.AccountMvc.M;
import kr.lul.kobalttown.page.account.AccountMvc.V;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static java.lang.Integer.MAX_VALUE;
import static java.util.concurrent.ThreadLocalRandom.current;
import static kr.lul.kobalttown.account.domain.Account.NICKNAME_MAX_LENGTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountWebTestConfiguration.class)
public class AccountControllerImplTest {
  private static final Logger log = getLogger(AccountControllerImplTest.class);

  @Autowired
  private AccountController controller;

  @Before
  public void setUp() throws Exception {
    assertThat(this.controller).isNotNull();
  }

  @Test
  public void test_createForm_with_null_model() throws Exception {
    assertThatThrownBy(() -> this.controller.createForm(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("model is null.");
  }

  @Test
  public void test_createForm_with_model() throws Exception {
    // GIVEN
    Model model = new ExtendedModelMap();
    log.info("GIVEN - model={}", model);

    // WHEN
    String template = this.controller.createForm(model);
    log.info("WHEN - template={}", template);

    // THEN
    assertThat(template)
        .isEqualTo(V.CREATE_FORM);
    assertThat(model)
        .hasFieldOrProperty(M.CREATE_REQ);
  }

  @Test
  public void test_create_with_null_req_and_null_binding_and_null_model() throws Exception {
    assertThatThrownBy(() -> this.controller.create(null, null, null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("createReq is null.");
  }

  @Test
  public void test_create_with_req_and_binding_and_model() throws Exception {
    // GIVEN
    String nickname = "nickname #" + current().nextInt(1, NICKNAME_MAX_LENGTH - 9);
    String email = "just.burrow." + current().nextInt(MAX_VALUE) + "@lul.kr";
    String password = "password";
    CreateAccountReq createReq = new CreateAccountReq(nickname, email, password, password);
    log.info("GIVEN - createReq={}", createReq);

    BindingResult binding = new BeanPropertyBindingResult(createReq, M.CREATE_REQ);
    Model model = new ExtendedModelMap();
    log.info("GIVEN - binding={}, model={}", binding, model);

    // WHEN
    String template = this.controller.create(createReq, binding, model);
    log.info("WHEN - template={}", template);

    // THEN
    assertThat(binding.hasErrors())
        .isFalse();
    assertThat(template)
        .startsWith("redirect:/");
  }
}
