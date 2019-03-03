package kr.lul.kobalttown.account.jpa.entity;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;
import kr.lul.kobalttown.account.jpa.mapping.AccountMapping;
import kr.lul.kobalttown.account.jpa.mapping.CredentialMapping.E;
import kr.lul.kobalttown.account.jpa.mapping.CredentialMapping.T;
import kr.lul.kobalttown.support.spring.jpa.entity.CreatableMappedSuperclass;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

import static kr.lul.kobalttown.common.util.Arguments.*;

/**
 * @author justburrow
 * @since 2019-02-27
 */
@Entity(name = E.NAME)
@Table(name = T.NAME,
    uniqueConstraints = {@UniqueConstraint(name = T.UQ_CREDENTIAL_PUBLIC_KEY, columnNames = {T.COL_PUBLIC_KEY})})
public class CredentialEntity extends CreatableMappedSuperclass implements Credential {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = T.COL_ID, nullable = false, insertable = false, updatable = false)
  private long id;
  @ManyToOne(targetEntity = AccountEntity.class)
  @JoinColumn(name = T.COL_ACCOUNT, nullable = false, updatable = false,
      foreignKey = @ForeignKey(name = T.FK_CREDENTIAL_PK_ACCOUNT), referencedColumnName = AccountMapping.T.COL_ID)
  private Account account;
  @Column(name = T.COL_PUBLIC_KEY, nullable = false, unique = true, updatable = false)
  private String publicKey;
  @Column(name = T.COL_SECRET_HASH, nullable = false, updatable = false)
  private String secretHash;

  private CredentialEntity() {  // JPA only
  }

  public CredentialEntity(Account account, String publicKey, String secretHash, Instant createdAt) {
    super(createdAt);
    notNull(account, "account");
    notEmpty(publicKey, "publicKey");
    matches(secretHash, SECRET_HASH_REGEX, "secretHash");

    this.account = account;
    this.publicKey = publicKey;
    this.secretHash = secretHash;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.account.domain.Credential
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public long getId() {
    return this.id;
  }

  @Override
  public Account getAccount() {
    return this.account;
  }

  @Override
  public String getPublicKey() {
    return this.publicKey;
  }

  @Override
  public String getSecretHash() {
    return this.secretHash;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (!(o instanceof CredentialEntity)) {
      return false;
    }
    CredentialEntity that = (CredentialEntity) o;
    return this.id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  @Override
  public String toString() {
    return new StringBuilder(CredentialEntity.class.getSimpleName())
        .append("{id=").append(this.id)
        .append(", account=").append(this.account.simpleToString())
        .append(", publicKey='").append(this.publicKey).append('\'')
        .append(", secretHash=[ PROTECTED ]").toString();
  }
}