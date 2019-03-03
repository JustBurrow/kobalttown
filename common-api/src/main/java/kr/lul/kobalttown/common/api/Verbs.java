package kr.lul.kobalttown.common.api;

/**
 * 기본 동사.
 *
 * @author justburrow
 * @since 2019-02-27
 */
public enum Verbs implements Verb {
  CREATE,
  READ,
  UPDATE,
  DELETE;

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.common.api.Verb
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String getName() {
    return name();
  }
}