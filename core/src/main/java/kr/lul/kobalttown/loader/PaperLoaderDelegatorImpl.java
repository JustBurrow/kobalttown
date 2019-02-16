package kr.lul.kobalttown.loader;

import kr.lul.kobalttown.domain.Paper;
import kr.lul.kobalttown.domain.PaperLoader;
import kr.lul.kobalttown.domain.PaperNotFoundException;
import kr.lul.kobalttown.domain.Papermark;
import org.slf4j.Logger;

import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.unmodifiableList;
import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Lists.immutable;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-01-05
 */
public class PaperLoaderDelegatorImpl implements PaperLoaderDelegator {
  private static final Logger log = getLogger(PaperLoaderDelegatorImpl.class);

  private List<PaperLoader> loaders;

  private void checkStatus() {
    if (null != this.loaders) {
      throw new IllegalStateException(format("already initialized : %s", this.loaders));
    }
  }

  public List<PaperLoader> init(PaperLoader... loaders) {
    notNull(loaders, "loaders");
    checkStatus();

    this.loaders = immutable(loaders);

    return this.loaders;
  }

  public List<PaperLoader> init(List<PaperLoader> loaders) {
    notNull(loaders, "loaders");
    checkStatus();

    this.loaders = unmodifiableList(loaders);

    return this.loaders;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.loader.PaperLoaderDelegator
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Paper load(Papermark papermark) throws PaperNotFoundException {
    if (log.isTraceEnabled()) {
      log.trace("args : papermark={}", papermark);
    }
    notNull(papermark, "papermark");

    for (PaperLoader loader : this.loaders) {
      if (loader.isSupported(papermark.getClass())) {
        Paper paper = loader.load(papermark);

        if (null != paper) {
          return paper;
        }
      }
    }

    throw new PaperNotFoundException(papermark);
  }
}