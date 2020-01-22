package kr.lul.kobalttown.account.data.entity;

import kr.lul.common.util.ContinuousRange;
import kr.lul.common.util.Range;
import kr.lul.common.util.Texts;
import kr.lul.kobalttown.account.data.mapping.AccountMapping;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.ValidationCode;
import kr.lul.kobalttown.account.domain.ValidationCodeStatusException;
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
import static kr.lul.kobalttown.account.domain.ValidationCode.Status.*;

/**
 * @author justburrow
 * @since 2020/01/03
 */
@Entity(name = ENTITY)
@Table(name = TABLE,
    uniqueConstraints = {@UniqueConstraint(name = UQ_VALIDATION_CODE, columnNames = {COL_CODE})},
    indexes = {@Index(name = FK_VALIDATION_CODE_PK_ACCOUNT, columnList = FK_VALIDATION_CODE_PK_ACCOUNT_COLUMNS),
        @Index(name = IDX_VALIDATION_CODE_EMAIL, columnList = IDX_VALIDATION_CODE_EMAIL_COLUMNS),
        @Index(name = IDX_VALIDATION_CODE_STATUS, columnList = IDX_VALIDATION_CODE_STATUS_COLUMNS)})
public class ValidationCodeEntity extends SavableEntity implements ValidationCode {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = COL_ID, nullable = false, unique = true, insertable = false, updatable = false)
  private long id;
  @ManyToOne(targetEntity = AccountEntity.class)
  @JoinColumn(name = COL_ACCOUNT, nullable = false, updatable = false,
      foreignKey = @ForeignKey(name = FK_VALIDATION_CODE_PK_ACCOUNT), referencedColumnName = AccountMapping.COL_ID)
  private Account account;
  @Column(name = COL_EMAIL, length = EMAIL_MAX_LENGTH, nullable = false, updatable = false)
  private String email;
  @Column(name = COL_CODE, length = CODE_LENGTH, nullable = false, unique = true, updatable = false)
  private String code;
  @Column(name = COL_EXPIRE_AT, nullable = false, updatable = false)
  private Instant expireAt;
  @Column(name = COL_STATUS, nullable = false)
  private Status status;
  @Column(name = COL_STATUS_AT, nullable = false)
  private Instant statusAt;

  @Transient
  private Range<Instant> validRange;

  public ValidationCodeEntity() {// JPA only
  }

  public ValidationCodeEntity(final Account account, final String email, final String code, final Instant createdAt) {
    this(account, email, code, TTL_DEFAULT, createdAt);
  }

  public ValidationCodeEntity(final Account account, final String email, final String code, final Duration ttl,
      final Instant createdAt) {
    this(account, email, code, createdAt.plus(ttl), createdAt);

    TTL_VALIDATOR.validate(ttl);
  }

  public ValidationCodeEntity(final Account account, final String email, final String code, final Instant expireAt,
      final Instant createdAt) {
    super(createdAt);

    ACCOUNT_VALIDATOR.validate(account);
    typeOf(account, AccountEntity.class, "account");
    EMAIL_VALIDATOR.validate(email);
    CODE_VALIDATOR.validate(code);
    EXPIRE_AT_VALIDATOR.validate(expireAt);
    TTL_VALIDATOR.validate(Duration.between(createdAt, expireAt));

    this.account = account;
    this.email = email;
    this.code = code;
    this.expireAt = expireAt;
    this.status = ISSUED;
    this.statusAt = createdAt;

    initExtra();
  }

  private void initExtra() {
    try {
      this.validRange = new ContinuousRange<>(this.createdAt.plus(USE_INTERVAL_MIN), true, this.expireAt, true);
    } catch (final Exception e) {
      throw new IllegalArgumentException("illegal expireAt : " + this.expireAt, e);
    }

    if (!this.validRange.isInclude(this.expireAt))
      throw new IllegalArgumentException(
          format("invalid expireAt : expireAt=%s, validRange=%s", this.expireAt, this.validRange));
  }

  @PostLoad
  private void postLoad() {
    initExtra();
  }

  private void lazyExpire(final Instant now) {
    if (ISSUED != this.status)
      return;

    if (this.expireAt.isBefore(now)) {
      this.status = EXPIRED;
      this.statusAt = now;
      this.updatedAt = now;
    }
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
  public String getEmail() {
    return this.email;
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
  public Status getStatus() {
    return this.status;
  }

  @Override
  public Instant getStatusAt() {
    return this.statusAt;
  }

  @Override
  public boolean isUsed() {
    return USED == this.status;
  }

  @Override
  public boolean isExpired() {
    return EXPIRED == this.status;
  }

  @Override
  public Range<Instant> getValidRange() {
    return this.validRange;
  }

  @Override
  public boolean isValid(final Instant now) {
    notNull(now, "now");

    return isValid() &&
               this.validRange.isInclude(now);
  }

  @Override
  public void use(final Instant now) throws IllegalStateException {
    notNull(now, "now");

    lazyExpire(now);

    if (this.createdAt.plus(USE_INTERVAL_MIN).isAfter(now))
      throw new IllegalArgumentException(format("too early use : now=%s, validRange=%s", now, this.validRange));
    else if (!this.status.valid())
      throw new ValidationCodeStatusException(this.status, USED, "invalidated at " + now);

    this.status = USED;
    this.statusAt = now;
    this.updatedAt = now;
    this.account.enable(now);
  }

  @Override
  public void expire(final Instant now) throws ValidationCodeStatusException {
    notNull(now, "now");

    if (now.isBefore(this.expireAt))
      throw new IllegalArgumentException(format("too early expire : now=%s, expireAt=%s", now, this.expireAt));
    else if (!this.status.valid())
      throw new ValidationCodeStatusException(this.status, EXPIRED, "not valid status.");

    this.status = EXPIRED;
    this.statusAt = now;
    this.updatedAt = now;
  }

  @Override
  public void inactive(final Instant now) throws ValidationCodeStatusException {
    notNull(now, "now");

    if (!this.status.valid())
      throw new ValidationCodeStatusException(this.status, INACTIVE, "not valid status.");
    else if (now.isBefore(this.updatedAt))
      throw new IllegalArgumentException(format("too early inactive : now=%s, updatedAt=%s", now, this.updatedAt));

    this.status = INACTIVE;
    this.statusAt = now;
    this.updatedAt = now;
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
               .append(", email=").append(this.email)
               .append(", code=").append(Texts.singleQuote(this.code))
               .append(", expireAt=").append(this.expireAt)
               .append(", status=").append(this.status)
               .append(", statusAt=").append(this.statusAt)
               .append(", ").append(super.toString())
               .append('}').toString();
  }
}
