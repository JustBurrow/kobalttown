package kr.lul.kobalttown.account.data.repository;

import kr.lul.kobalttown.account.data.entity.CredentialEntity;
import kr.lul.kobalttown.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@Repository
public interface CredentialRepository extends JpaRepository<CredentialEntity, Long> {
  CredentialEntity findByPublicKey(String publicKey);

  List<CredentialEntity> findAllByAccount(Account account);

  boolean existsByPublicKey(String publicKey);
}
