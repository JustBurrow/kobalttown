package kr.lul.kobalttown.loader;

import kr.lul.kobalttown.domain.Paper;
import kr.lul.kobalttown.domain.PaperNotFoundException;
import kr.lul.kobalttown.domain.Papermark;
import kr.lul.kobalttown.domain.VerbPathPapermark;
import org.slf4j.Logger;

import java.nio.file.Path;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-01-05
 */
public abstract class AbstractVerbPathPaperLoader<P extends Paper> implements VerbPathPaperLoader<P> {
  private static final Logger log = getLogger(AbstractVerbPathPaperLoader.class);

  /**
   * @param path 요청 경로.
   *
   * @return 생성된 페이퍼.
   */
  protected abstract P create(Path path);

  /**
   * @param path 요청 경로.
   *
   * @return 경로에 해당하는 페이퍼.
   */
  protected abstract P read(Path path);

  /**
   * @param path 요청 경로.
   *
   * @return 갱신 결과에 관한 페이퍼.
   */
  protected abstract P update(Path path);

  /**
   * @param path 요청 경로.
   *
   * @return 삭제 결과에 관한 페이퍼.
   */
  protected abstract P delete(Path path);

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.loader.VerbPathPaperLoader
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean isSupported(Class<? extends Papermark> clz) {
    if (log.isTraceEnabled()) {
      log.trace("args : clz={}", clz);
    }
    return VerbPathPapermark.class == clz;
  }

  @Override
  public P load(VerbPathPapermark papermark) throws PaperNotFoundException {
    if (log.isTraceEnabled()) {
      log.trace("args : papermark={}", papermark);
    }
    notNull(papermark, "papermark");

    final P paper;
    switch (papermark.getVerb()) {
      case CREATE:
        paper = create(papermark.getPath());
        break;
      case READ:
        paper = read(papermark.getPath());
        break;
      case UPDATE:
        paper = update(papermark.getPath());
        break;
      case DELETE:
        paper = delete(papermark.getPath());
        break;
      default:
        throw new IllegalArgumentException(format("unsupported verb : %s", papermark.getVerb()));
    }

    if (null == paper) {
      PaperNotFoundException e = new PaperNotFoundException(papermark);
      if (log.isInfoEnabled()) {
        log.info(e.getMessage(), e);
      }
      throw e;
    } else {
      if (log.isTraceEnabled()) {
        log.trace("return : {}", paper);
      }
      return paper;
    }
  }
}