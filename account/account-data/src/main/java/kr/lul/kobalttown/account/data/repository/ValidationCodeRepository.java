package kr.lul.kobalttown.account.data.repository;

import kr.lul.kobalttown.account.data.entity.AccountEntity;
import kr.lul.kobalttown.account.data.entity.ValidationCodeEntity;
import kr.lul.kobalttown.account.domain.ValidationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author justburrow
 * @since 2020/01/03
 */
@Repository
public interface ValidationCodeRepository extends JpaRepository<ValidationCodeEntity, Long> {
  boolean existsByCode(String code);

  List<ValidationCode> findAllByAccount(AccountEntity account);
}
