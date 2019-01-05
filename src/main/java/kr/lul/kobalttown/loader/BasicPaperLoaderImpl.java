package kr.lul.kobalttown.loader;

import kr.lul.kobalttown.domain.BasicPapermark;
import kr.lul.kobalttown.domain.Paper;
import kr.lul.kobalttown.domain.PaperNotFoundException;
import kr.lul.kobalttown.domain.Papermark;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import static kr.lul.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-01-05
 */
@Service
class BasicPaperLoaderImpl implements BasicPaperLoader {
  private static final Logger log = getLogger(BasicPaperLoaderImpl.class);

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.loader.BasicPaperLoader
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean isSupported(Class<? extends Papermark> clz) {
    if (log.isTraceEnabled()) {
      log.trace("args : clz={}", clz);
    }
    return BasicPapermark.class == clz;
  }

  @Override
  public Paper load(BasicPapermark papermark) throws PaperNotFoundException {
    if (log.isTraceEnabled()) {
      log.trace("args : papermark={}", papermark);
    }
    notNull(papermark, "papermark");

    // TODO

    return null;
  }
}