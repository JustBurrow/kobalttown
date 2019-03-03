package kr.lul.kobalttown.support.spring.jpa.entity;

import kr.lul.kobalttown.common.util.Creatable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

import static kr.lul.kobalttown.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2019-02-28
 */
@MappedSuperclass
public abstract class CreatableMappedSuperclass implements Creatable {
  public static final String ATTR_CREATED_AT = "createdAt";
  public static final String COL_CREATED_AT = "created_at";

  @Column(name = COL_CREATED_AT, nullable = false, updatable = false)
  private Instant createdAt;

  protected CreatableMappedSuperclass(Instant createdAt) {
    notNull(createdAt, "createdAt");
    this.createdAt = createdAt;
  }

  @Override
  public Instant getCreatedAt() {
    return this.createdAt;
  }
}