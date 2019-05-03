package kr.lul.kobalttown.account.jpa.repository;

import kr.lul.kobalttown.account.jpa.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author justburrow
 * @since 2019-02-27
 */
@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
  boolean existsByNickname(String nickname);

  AccountEntity findByNickname(String nickname);

  AccountEntity findOneById(int id);
}