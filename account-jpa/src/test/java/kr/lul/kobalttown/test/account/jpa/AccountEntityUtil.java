package kr.lul.kobalttown.test.account.jpa;

import kr.lul.kobalttown.account.jpa.entity.AccountEntity;
import kr.lul.kobalttown.account.jpa.repository.AccountRepository;
import kr.lul.kobalttown.common.util.TimeProvider;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.Instant;

import static kr.lul.kobalttown.test.account.domain.AccountUtils.nickname;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-02
 */
public class AccountEntityUtil {
  private static final Logger log = getLogger(AccountEntityUtil.class);

  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private TimeProvider timeProvider;

  @PostConstruct
  private void postConstruct() {
    assertThat(this.accountRepository).isNotNull();
    assertThat(this.timeProvider).isNotNull();
  }

  /**
   * 제약조건을 위반하지 않는 새 계정을 반환한다.
   *
   * @return 새로 저장할 수 있는 계정.
   */
  public AccountEntity freshAccount() {
    Instant now = this.timeProvider.now();
    String nickname;
    do {
      nickname = nickname();
    } while (this.accountRepository.existsByNickname(nickname));

    AccountEntity account = new AccountEntity(now, nickname);

    if (log.isTraceEnabled()) {
      log.trace("return : {}", account);
    }
    return account;
  }
}