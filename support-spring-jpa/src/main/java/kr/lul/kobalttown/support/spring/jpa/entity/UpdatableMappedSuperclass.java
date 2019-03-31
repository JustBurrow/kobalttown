package kr.lul.kobalttown.support.spring.jpa.entity;

import kr.lul.kobalttown.common.util.Updatable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

import static java.lang.String.format;
import static kr.lul.kobalttown.common.util.Arguments.ae;
import static kr.lul.kobalttown.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2019-02-28
 */
@MappedSuperclass
public abstract class UpdatableMappedSuperclass implements Updatable {
  public static final String ATTR_CREATED_AT = CreatableMappedSuperclass.ATTR_CREATED_AT;
  public static final String COL_CREATED_AT = CreatableMappedSuperclass.COL_CREATED_AT;

  public static final String ATTR_UPDATED_AT = "updatedAt";
  public static final String COL_UPDATED_AT = "updated_at";

  @Column(name = COL_CREATED_AT, nullable = false, updatable = false)
  private Instant createdAt;
  @Column(name = COL_UPDATED_AT, nullable = false)
  private Instant updatedAt;

  protected UpdatableMappedSuperclass() { // JPA only
  }

  protected UpdatableMappedSuperclass(Instant createdAt) {
    notNull(createdAt, "createdAt");

    this.createdAt = createdAt;
    setUpdatedAt(createdAt);
  }

  protected void setUpdatedAt(Instant updatedAt) {
    ae(updatedAt, this.createdAt, "updatedAt");

    this.updatedAt = updatedAt;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.common.util.Updatable
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Instant getCreatedAt() {
    return this.createdAt;
  }

  @Override
  public Instant getUpdatedAt() {
    return this.updatedAt;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return format("%s=%s, %s=%s", ATTR_CREATED_AT, this.createdAt, ATTR_UPDATED_AT, this.updatedAt);
  }
}