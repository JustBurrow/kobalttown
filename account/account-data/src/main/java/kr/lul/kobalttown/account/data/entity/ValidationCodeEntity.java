package kr.lul.kobalttown.account.data.entity;

import kr.lul.common.util.ContinuousRange;
import kr.lul.common.util.Range;
import kr.lul.common.util.Texts;
import kr.lul.kobalttown.account.data.mapping.AccountMapping;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.ExpiredValidationCodeException;
import kr.lul.kobalttown.account.domain.UsedValidationCodeException;
import kr.lul.kobalttown.account.domain.ValidationCode;
import kr.lul.support.spring.data.jpa.entiy.SavableEntity;

import javax.persistence.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

import static java.lang.String.format;
import static javax.persistence.GenerationType.IDENTITY;
import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Arguments.typeOf;
import static kr.lul.kobalttown.account.data.mapping.ValidationCodeMapping.*;

/**
 * @author justburrow
 * @since 2020/01/03
 */
@Entity(name = ENTITY)
@Table(name = TABLE,
    uniqueConstraints = {@UniqueConstraint(name = UQ_VALIDATION_CODE, columnNames = {COL_CODE})},
    indexes = {@Index(name = FK_VALIDATION_CODE_PK_ACCOUNT, columnList = FK_VALIDATION_CODE_PK_ACCOUNT_COLUMNS),
        @Index(name = IDX_VALIDATION_CODE_VALID, columnList = IDX_VALIDATION_CODE_VALID_COLUMNS)})
public class ValidationCodeEntity extends SavableEntity implements ValidationCode {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = COL_ID, nullable = false, unique = true, insertable = false, updatable = false)
  private long id;
  @ManyToOne(targetEntity = AccountEntity.class)
  @JoinColumn(name = COL_ACCOUNT, nullable = false, updatable = false,
      foreignKey = @ForeignKey(name = FK_VALIDATION_CODE_PK_ACCOUNT), referencedColumnName = AccountMapping.COL_ID)
  private Account account;
  @Column(name = COL_CODE, length = CODE_LENGTH, nullable = false, unique = true, updatable = false)
  private String code;
  @Column(name = COL_EXPIRE_AT, nullable = false, updatable = false)
  private Instant expireAt;
  @Column(name = COL_USED_AT, insertable = false)
  private Instant usedAt;
  @Column(name = COL_EXPIRED_AT, insertable = false)
  private Instant expiredAt;

  @Transient
  private Range<Instant> validRange;

  public ValidationCodeEntity() {// JPA only
  }

  public ValidationCodeEntity(final Account account, final String code, final Instant createdAt) {
    this(account, code, TTL_DEFAULT, createdAt);
  }

  public ValidationCodeEntity(final Account account, final String code, final Duration ttl, final Instant createdAt) {
    this(account, code, createdAt.plus(ttl), createdAt);
  }

  public ValidationCodeEntity(final Account account, final String code, final Instant expireAt,
      final Instant createdAt) {
    super(createdAt);

    ACCOUNT_VALIDATOR.validate(account);
    typeOf(account, AccountEntity.class, "account");

    CODE_VALIDATOR.validate(code);
    EXPIRE_AT_VALIDATOR.validate(expireAt);

    final Range<Instant> validRange = new ContinuousRange<>(createdAt.plus(MIN_USE_INTERVAL), true,
        expireAt, true);
    if (!validRange.isInclude(expireAt)) {
      throw new IllegalArgumentException(format("invalid expireAt : expireAt=%s, validRange=%s", expireAt, validRange));
    }

    this.account = account;
    this.code = code;
    this.expireAt = expireAt;
    this.usedAt = null;
    this.expiredAt = null;

    initExtra();
  }

  private void initExtra() {
    this.validRange = new ContinuousRange<>(this.createdAt.plus(MIN_USE_INTERVAL), true, this.expireAt, true);
  }

  @PostLoad
  private void postLoad() {
    initExtra();
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.account.domain.ValidationCode
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
  public String getCode() {
    return this.code;
  }

  @Override
  public Instant getExpireAt() {
    return this.expireAt;
  }

  @Override
  public Instant getUsedAt() {
    return this.usedAt;
  }

  @Override
  public Instant getExpiredAt() {
    return this.expiredAt;
  }

  @Override
  public Range<Instant> getValidRange() {
    return this.validRange;
  }

  @Override
  public boolean isValid(final Instant when) {
    notNull(when, "when");

    return !isUsed() &&
               !isExpired() &&
               this.validRange.isInclude(when);
  }

  @Override
  public void use(final Instant when) throws IllegalStateException {
    notNull(when, "when");
    if (this.createdAt.plus(MIN_USE_INTERVAL).isAfter(when))
      throw new IllegalArgumentException(format("too early use : when=%s, validRange=%s", when, this.validRange));

    if (this.expireAt.isBefore(when)) {
      expire(when);
      throw new ExpiredValidationCodeException(when);
    }

    if (isUsed())
      throw new UsedValidationCodeException(this.usedAt);
    if (this.account.isEnabled())
      throw new IllegalStateException("already enabled account.");

    this.usedAt = when;
    this.updatedAt = when;
    this.account.enable(when);
  }

  @Override
  public void expire(final Instant when) {
    notNull(when, "when");
    if (this.expireAt.equals(when) || this.expireAt.isAfter(when))
      throw new IllegalArgumentException(format("too early expire : when=%s, expireAt=%s", when, this.expireAt));

    if (isExpired())
      throw new ExpiredValidationCodeException(this.expiredAt);
    if (isUsed())
      throw new UsedValidationCodeException(this.usedAt);

    this.expiredAt = when;
    this.updatedAt = when;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (0L >= this.id || o == null || getClass() != o.getClass()) return false;
    return this.id == ((ValidationCodeEntity) o).id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  @Override
  public String toString() {
    return new StringBuilder(ValidationCodeEntity.class.getSimpleName())
               .append("id=").append(this.id)
               .append(", account=").append(this.account.getId())
               .append(", code=").append(Texts.singleQuote(this.code))
               .append(", expireAt=").append(this.expireAt)
               .append(", usedAt=").append(this.usedAt)
               .append(", expiredAt=").append(this.expiredAt)
               .append(", ").append(super.toString())
               .append('}').toString();
  }
}
