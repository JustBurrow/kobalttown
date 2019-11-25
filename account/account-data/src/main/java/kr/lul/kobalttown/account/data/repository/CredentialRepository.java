package kr.lul.kobalttown.account.data.repository;

import kr.lul.kobalttown.account.data.entity.CredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@Repository
public interface CredentialRepository extends JpaRepository<CredentialEntity, Long> {
}
