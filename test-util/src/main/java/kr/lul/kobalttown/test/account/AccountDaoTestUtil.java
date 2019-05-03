package kr.lul.kobalttown.test.account;

import kr.lul.kobalttown.account.dao.AccountDao;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;
import kr.lul.kobalttown.account.jpa.entity.AccountEntity;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-10
 */
public class AccountDaoTestUtil extends AccountJpaTestUtil {
  private static final Logger log = getLogger(AccountDaoTestUtil.class);

  @Autowired
  protected AccountDao accountDao;

  @PostConstruct
  private void postConstruct() {
    assertThat(this.accountDao).isNotNull();
  }

  public Account createdAccount() {
    return this.accountDao.create(prePersistAccount());
  }

  public Credential createdCredential() {
    return this.accountDao.create(prePersistCredential((AccountEntity) createdAccount()));
  }
}