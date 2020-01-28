package kr.lul.kobalttown.document.domain;

/**
 * 대댓글.
 *
 * @author justburrow
 * @since 2020/01/29
 */
public interface Recomment<D extends Document<?>> extends Comment<D> {
  /**
   * @return 대상 댓글.
   */
  Comment<D> getComment();
}
