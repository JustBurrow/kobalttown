package kr.lul.kobalttown.configuration.jpa;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author justburrow
 * @since 2019-03-03
 */
@Configuration
@EnableJpaRepositories(basePackages = "kr.lul.kobalttown")
@EnableTransactionManagement
public class ConfigurationJpaConfiguration {
  @Bean
  @ConfigurationProperties("spring.datasource")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }

  /**
   * JPA 엔티티 관리자 팩토리.
   * DB의 데이터와 동기화되는 단위인 JPA 엔티티를 관리하는 엔티티 관리자를 만든다.
   * 엔티티 관리자는 데이터 동기화 시점 등을 결정한다.
   *
   * @return
   */
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
    adapter.setDatabase(Database.MYSQL);

    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setDataSource(dataSource());
    factory.setPackagesToScan("kr.lul.kobalttown");
    factory.setJpaVendorAdapter(adapter);

    return factory;
  }

  /**
   * JPA 이벤트를 기반으로 트랜잭션을 관리하는 트랜잭션 관리자 컴포넌트를 만든다.
   *
   * @return
   */
  @Bean
  public PlatformTransactionManager transactionManager() {
    return new JpaTransactionManager(entityManagerFactory().getObject());
  }

  /**
   * JPA 구현체로 사용하는 Hibernate에서 발생한 예외를 스프링의 예외로 변환한다.
   *
   * @return
   */
  @Bean
  public HibernateExceptionTranslator hibernateExceptionTranslator() {
    return new HibernateExceptionTranslator();
  }
}