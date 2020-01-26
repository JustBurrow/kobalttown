package kr.lul.kobalttown.account.data.repository;

import kr.lul.kobalttown.account.data.entity.AccountEntity;
import kr.lul.kobalttown.account.data.entity.EnableCodeEntity;
import kr.lul.kobalttown.account.domain.EnableCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author justburrow
 * @since 2020/01/03
 */
@Repository
public interface EnableCodeRepository extends JpaRepository<EnableCodeEntity, Long> {
  EnableCodeEntity findOneByToken(String token);

  boolean existsByToken(String code);

  List<EnableCode> findAllByAccount(AccountEntity account);

  List<EnableCodeEntity> findAllByEmailAndStatusIn(String email, List<EnableCode.Status> statuses);
}
