package kr.lul.kobalttown.account.web.controller;

import kr.lul.common.data.Context;
import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.borderline.AccountBorderline;
import kr.lul.kobalttown.account.borderline.command.CreateAccountCmd;
import kr.lul.kobalttown.account.data.repository.AccountRepository;
import kr.lul.kobalttown.account.dto.AccountDetailDto;
import kr.lul.kobalttown.account.web.AccountWebTestConfiguration;
import kr.lul.kobalttown.account.web.controller.request.CreateAccountReq;
import kr.lul.kobalttown.page.account.AccountMvc.M;
import kr.lul.kobalttown.page.account.AccountMvc.V;
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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.time.Instant;
import java.util.List;

import static kr.lul.kobalttown.account.domain.AccountUtil.nickname;
import static kr.lul.kobalttown.account.domain.CredentialUtil.email;
import static kr.lul.kobalttown.account.domain.CredentialUtil.userKey;
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
  @Autowired
  private AccountBorderline borderline;
  @Autowired
  private AccountRepository repository;
  @Autowired
  private TimeProvider timeProvider;

  private Instant before;

  @Before
  public void setUp() throws Exception {
    assertThat(this.controller).isNotNull();
    assertThat(this.borderline).isNotNull();
    assertThat(this.repository).isNotNull();
    assertThat(this.timeProvider).isNotNull();

    this.before = this.timeProvider.now();
    log.info("SETUP - before={}", this.before);
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
    final Model model = new ExtendedModelMap();
    log.info("GIVEN - model={}", model);

    // WHEN
    final String template = this.controller.createForm(model);
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
  public void test_create() throws Exception {
    // GIVEN
    final CreateAccountReq createReq = new CreateAccountReq(nickname(), email(), userKey(), "password", "password");
    log.info("GIVEN - createReq={}", createReq);

    final BindingResult binding = new BeanPropertyBindingResult(createReq, M.CREATE_REQ);
    final Model model = new ExtendedModelMap();
    log.info("GIVEN - binding={}, model={}", binding, model);

    // WHEN
    final String template = this.controller.create(createReq, binding, model);
    log.info("WHEN - template={}", template);

    // THEN
    assertThat(binding.hasErrors())
        .isFalse();
    assertThat(template)
        .startsWith("redirect:/");
  }

  @Test
  public void test_detail_with_null_user() throws Exception {
    assertThatThrownBy(() -> this.controller.profile(null, 1L, new ExtendedModelMap()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("user is null.");
  }

  @Test
  public void test_detail_with_id_0() throws Exception {
    assertThatThrownBy(
        () -> this.controller.profile(new User(1L, "testuser", "", List.of(new SimpleGrantedAuthority("ROLE_USER"))),
            0L, new ExtendedModelMap()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("id is not positive");
  }

  @Test
  public void test_detail() throws Exception {
    // GIVEN
    final User user = new User(1L, "testuser", "", List.of(new SimpleGrantedAuthority("ROLE_USER")));
    log.info("GIVEN - user={}", user);
    final AccountDetailDto expected = this.borderline.create(
        new CreateAccountCmd(new Context(), nickname(), email(), userKey(), "password", this.before));
    log.info("GIVEN - expected={}", expected);

    final Model model = new ExtendedModelMap();
    log.info("GIVEN - model={}", model);

    // WHEN
    final String view = this.controller.profile(user, expected.getId(), model);
    log.info("WHEN - view={}", view);

    // THEN
    assertThat(view)
        .isEqualTo(V.DETAIL);
    assertThat(model.getAttribute(M.ACCOUNT))
        .isNotNull()
        .isEqualTo(expected);
  }
}
