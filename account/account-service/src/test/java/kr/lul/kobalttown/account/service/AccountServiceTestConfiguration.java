package kr.lul.kobalttown.account.service;

import kr.lul.kobalttown.configuration.data.jpa.JpaConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@SpringBootApplication
@Import({JpaConfiguration.class})
public class AccountServiceTestConfiguration {
}
