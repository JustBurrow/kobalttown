package kr.lul.kobalttown.loader;

import kr.lul.kobalttown.domain.Paper;
import kr.lul.kobalttown.domain.PaperLoader;
import kr.lul.kobalttown.domain.PaperNotFoundException;
import kr.lul.kobalttown.domain.Papermark;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-01-05
 */
@Service
class PaperLoaderDelegatorImpl implements PaperLoaderDelegator {
  private static final Logger log = getLogger(PaperLoaderDelegatorImpl.class);

  @Autowired
  private BasicPaperLoader basicPapermarkLoader;

  private List<PaperLoader> loaders;

  @PostConstruct
  private void postConstruct() {
    this.loaders = List.of(this.basicPapermarkLoader);
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

    throw new PaperNotFoundException(format("paper does not exist : papermark=%s", papermark));
  }
}