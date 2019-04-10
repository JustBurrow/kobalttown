package kr.lul.kobalttown.account.jpa.repository;

import kr.lul.kobalttown.account.jpa.entity.AccountEntity;
import kr.lul.kobalttown.account.jpa.entity.CredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author justburrow
 * @since 2019-02-28
 */
@Repository
public interface CredentialRepository extends JpaRepository<CredentialEntity, Long> {
  boolean existsByPublicKey(String publicKey);

  CredentialEntity findByPublicKey(String publicKey);

  List<CredentialEntity> findAllByAccount(AccountEntity account);
}