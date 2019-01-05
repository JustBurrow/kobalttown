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
   * 로더가 대응할 수 있는 타입의 페이퍼마크인지 확인한다.
   *
   * @param clz 페이퍼마크의 타입.
   *
   * @return 지원하는 타입이면 {@code true}.
   */
  boolean isSupported(Class<? extends Papermark> clz);

  /**
   * 특정 {@link Paper}를 찾을 수 있는 정보를 사용해
   *
   * @param papermark 페이퍼마크.
   *
   * @return 페이퍼마크에 해당하는 페이퍼. <b>not null</b>.
   *
   * @throws PaperNotFoundException 페이퍼를 찾지 못했을 때.
   */
  Paper load(M papermark) throws PaperNotFoundException;
}