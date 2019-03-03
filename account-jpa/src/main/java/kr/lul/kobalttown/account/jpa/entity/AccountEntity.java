package kr.lul.kobalttown.account.jpa.entity;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.jpa.mapping.AccountMapping.E;
import kr.lul.kobalttown.account.jpa.mapping.AccountMapping.T;
import kr.lul.kobalttown.support.spring.jpa.entity.UpdatableMappedSuperclass;

import javax.persistence.*;
import java.time.Instant;

import static java.lang.String.format;
import static kr.lul.kobalttown.account.domain.Account.validateNickname;

/**
 * @author justburrow
 * @since 2019-02-27
 */
@Entity(name = E.NAME)
@Table(name = T.NAME,
    uniqueConstraints = {@UniqueConstraint(name = T.UQ_ACCOUNT_NICKNAME, columnNames = {T.COL_NICKNAME})})
public class AccountEntity extends UpdatableMappedSuperclass implements Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = T.COL_ID, nullable = false, insertable = false, updatable = false)
  private int id;
  @Column(name = T.COL_NICKNAME, nullable = false, unique = true, updatable = false)
  private String nickname;

  public AccountEntity(Instant createdAt, String nickname) {
    super(createdAt);
    validateNickname(nickname);

    this.nickname = nickname;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.account.domain.Account
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public String getNickname() {
    return this.nickname;
  }

  @Override
  public String simpleToString() {
    return format("(%d, %s)", this.id, this.nickname);
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AccountEntity)) {
      return false;
    }
    AccountEntity that = (AccountEntity) o;
    return this.id == that.id;
  }

  @Override
  public int hashCode() {
    return this.id;
  }

  @Override
  public String toString() {
    return new StringBuilder(AccountEntity.class.getSimpleName())
        .append("{id=").append(this.id)
        .append(", nickname='").append(this.nickname).append('\'')
        .append(", ").append(super.toString())
        .append('}').toString();
  }
}