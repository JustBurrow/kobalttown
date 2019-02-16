package kr.lul.kobalttown.loader;

import kr.lul.kobalttown.domain.Paper;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import static kr.lul.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * 로더 인스턴스에 등록된 {@link Paper} 인스턴스를 제공한다.
 *
 * @author justburrow
 * @since 2019-02-10
 */
public class InMemoryVerbPathPaperLoader extends AbstractVerbPathPaperLoader {
  private static final Logger log = getLogger(InMemoryVerbPathPaperLoader.class);

  private final Map<Path, Paper> papers;

  public InMemoryVerbPathPaperLoader() {
    this.papers = new HashMap<>();
  }

  public Paper addPaper(Paper paper) {
    if (log.isTraceEnabled()) {
      log.trace("args : paper={}", paper);
    }
    notNull(paper, "paper");

    Paper rv = this.papers.put(paper.getPath(), paper);

    if (log.isTraceEnabled()) {
      log.trace("return : {}", rv);
    }
    return rv;
  }

  private Paper get(Path path) {
    return this.papers.get(path);
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.loader.AbstractVerbPathPaperLoader
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Paper create(Path path) {
    if (log.isTraceEnabled()) {
      log.trace("args : path={}", path);
    }
    notNull(path, "path");

    return get(path);
  }

  @Override
  public Paper read(Path path) {
    if (log.isTraceEnabled()) {
      log.trace("args : path={}", path);
    }
    notNull(path, "path");

    return get(path);
  }

  @Override
  public Paper update(Path path) {
    if (log.isTraceEnabled()) {
      log.trace("args : path={}", path);
    }
    notNull(path, "path");

    return get(path);
  }

  @Override
  public Paper delete(Path path) {
    if (log.isTraceEnabled()) {
      log.trace("args : path={}", path);
    }
    notNull(path, "path");

    return get(path);
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return new StringJoiner(", ", InMemoryVerbPathPaperLoader.class.getSimpleName() + "[", "]")
        .add("papers=" + this.papers)
        .toString();
  }
}