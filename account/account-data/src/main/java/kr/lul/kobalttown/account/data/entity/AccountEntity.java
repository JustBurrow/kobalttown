package kr.lul.kobalttown.account.data.entity;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.support.spring.data.jpa.entiy.SavableEntity;

import javax.persistence.*;
import java.util.Objects;

import static java.lang.String.format;
import static kr.lul.kobalttown.account.data.mapping.AccountMapping.*;

/**
 * @author justburrow
 * @since 2019/11/23
 */
@Entity(name = ENTITY)
@Table(name = TABLE,
    uniqueConstraints = {@UniqueConstraint(name = UQ_NICKNAME, columnNames = {COL_NICKNAME})})
public class AccountEntity extends SavableEntity implements Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = COL_ID, nullable = false, insertable = false, updatable = false)
  private long id;
  @Column(name = COL_NICKNAME, nullable = false, unique = true)
  private String nickname;
  @Column(name = COL_ENABLED, nullable = false)
  private boolean enabled;

  public AccountEntity() {// JPA only
  }

  public AccountEntity(String nickname) {
    NICKNAME_VALIDATOR.validate(nickname);

    this.nickname = nickname;
  }

  @Override
  public long getId() {
    return this.id;
  }

  @Override
  public String getNickname() {
    return this.nickname;
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AccountEntity)) return false;
    return this.id == ((AccountEntity) o).id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  @Override
  public String toString() {
    return format("%s{id=%d, nickname='%s', enabled=%b, %s}", AccountEntity.class.getSimpleName(),
        this.id, this.nickname, this.enabled, super.toString());
  }
}
