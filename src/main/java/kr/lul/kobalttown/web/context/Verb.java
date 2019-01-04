package kr.lul.kobalttown.web.context;

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
  CREATE,
  /**
   * HTTP GET
   */
  READ,
  /**
   * HTTP PATCH
   */
  UPDATE,
  /**
   * HTTP DELETE
   */
  DELETE
}