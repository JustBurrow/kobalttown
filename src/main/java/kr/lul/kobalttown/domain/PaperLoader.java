package kr.lul.kobalttown.domain;

/**
 * {@link Paper}를 읽어 반환한다.
 *
 * @param <M> 지원하는 페이퍼마크 타입.
 *
 * @author justburrow
 * @since 2019-01-05
 */
public interface PaperLoader<M extends Papermark> {
  /**
   * 특정 {@link Paper}를 찾을 수 있는 정보를 사용해
   *
   * @param mark
   *
   * @return 페이퍼마크에 해당하는 페이퍼. <b>not null</b>.
   *
   * @throws PaperNotFoundException 페이퍼를 찾지 못했을 때.
   */
  Paper load(M mark) throws PaperNotFoundException;
}