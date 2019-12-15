package kr.lul.kobalttown.account.data.repository;

import kr.lul.kobalttown.account.data.AccountDataAnchor;
import kr.lul.kobalttown.configuration.data.jpa.JpaConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@SpringBootApplication(scanBasePackageClasses = {AccountDataAnchor.class})
@Import(JpaConfiguration.class)
public class AccountDataRepositoryTestConfiguration {
}
