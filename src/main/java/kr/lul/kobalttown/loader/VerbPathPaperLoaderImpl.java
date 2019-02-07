package kr.lul.kobalttown.loader;

import kr.lul.kobalttown.domain.Paper;
import kr.lul.kobalttown.domain.PaperNotFoundException;
import kr.lul.kobalttown.domain.Papermark;
import kr.lul.kobalttown.domain.VerbPathPapermark;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import static kr.lul.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-01-05
 */
@Service
class VerbPathPaperLoaderImpl implements VerbPathPaperLoader {
  private static final Logger log = getLogger(VerbPathPaperLoaderImpl.class);

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
  public Paper load(VerbPathPapermark papermark) throws PaperNotFoundException {
    if (log.isTraceEnabled()) {
      log.trace("args : papermark={}", papermark);
    }
    notNull(papermark, "papermark");

    // TODO

    return null;
  }
}