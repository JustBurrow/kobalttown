package kr.lul.kobalttown.account.data.entity;

import kr.lul.kobalttown.account.data.mapping.AccountMapping;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Texts.singleQuote;
import static kr.lul.kobalttown.account.data.mapping.CredentialMapping.*;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@Entity(name = ENTITY)
@Table(name = TABLE,
    indexes = @Index(name = FK_CREDENTIAL_PK_ACCOUNT, columnList = FK_CREDENTIAL_PK_ACCOUNT_COLUMNS),
    uniqueConstraints = @UniqueConstraint(name = UQ_CREDENTIAL_PUBLIC_KEY, columnNames = COL_PUBLIC_KEY))
public class CredentialEntity implements Credential {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = COL_ID, nullable = false, unique = true, insertable = false, updatable = false)
  private long id;
  @ManyToOne(targetEntity = AccountEntity.class)
  @JoinColumn(name = COL_ACCOUNT, nullable = false, updatable = false,
      foreignKey = @ForeignKey(name = FK_CREDENTIAL_PK_ACCOUNT), referencedColumnName = AccountMapping.COL_ID)
  private Account account;
  @Column(name = COL_PUBLIC_KEY, nullable = false, unique = true, updatable = false)
  private String publicKey;
  @Column(name = COL_SECRET_HASH, nullable = false, updatable = false)
  private String secretHash;
  @Column(name = COL_CREATED_AT, nullable = false, updatable = false)
  private Instant createdAt;

  public CredentialEntity() {// JPA only
  }

  public CredentialEntity(Account account, String publicKey, String secretHash, Instant createdAt) {
    ACCOUNT_VALIDATOR.validate(account);
    PUBLIC_KEY_VALIDATOR.validate(publicKey);
    SECRET_HASH_VALIDATOR.validate(secretHash);
    notNull(createdAt, ATTR_CREATED_AT);

    this.account = account;
    this.publicKey = publicKey;
    this.secretHash = secretHash;
    this.createdAt = createdAt;
  }

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

  @Override
  public Instant getCreatedAt() {
    return this.createdAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (0L >= this.id || !(o instanceof CredentialEntity)) return false;
    return this.id == ((CredentialEntity) o).id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  @Override
  public String toString() {
    return new StringBuilder(CredentialEntity.class.getSimpleName())
        .append("{id=").append(this.id)
        .append(", account=").append(this.account.getId())
        .append(", publicKey=").append(singleQuote(this.publicKey))
        .append(", secretHash=").append(singleQuote(this.secretHash))
        .append(", createdAt=").append(this.createdAt)
        .append('}')
        .toString();
  }
}
