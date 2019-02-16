package kr.lul.kobalttown.domain;

import java.util.Set;

import static kr.lul.common.util.Sets.immutable;

/**
 * CRUD 타입.
 *
 * @author justburrow
 * @since 2018-12-25
 */
public enum Verb {
  /**
   * HTTP POST
   */
  CREATE("post"),
  /**
   * HTTP GET
   */
  READ("get"),
  /**
   * HTTP PATCH
   */
  UPDATE("patch", "put"),
  /**
   * HTTP DELETE
   */
  DELETE("delete");

  /**
   * 별명으로 {@link Verb}를 찾아 반환한다.
   *
   * @param alias 별명.
   *
   * @return 별명에 해당하는 값. 없으면 {@code null}.
   */
  public static Verb byAlias(String alias) {
    if (null == alias) {
      throw new IllegalArgumentException("alias is null.");
    }

    for (Verb verb : Verb.values()) {
      if (verb.hasAlias(alias)) {
        return verb;
      }
    }

    return null;
  }

  /**
   * {@link Verb}의 별명. 대소문자를 구분하지 않는다.
   */
  private final Set<String> aliases;

  Verb(String... aliases) {
    this.aliases = immutable(aliases);
  }

  public boolean hasAlias(String alias) {
    if (null == alias) {
      throw new IllegalArgumentException("aliases is null.");
    }

    for (String a : this.aliases) {
      if (a.equalsIgnoreCase(alias)) {
        return true;
      }
    }

    return false;
  }
}