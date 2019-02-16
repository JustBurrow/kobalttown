package kr.lul.kobalttown.domain;

import kr.lul.common.util.Arguments;

import java.nio.file.Path;

/**
 * 동사와 경로를 기반으로 {@link Paper}를 특정할 수 있는 페이퍼마크.
 *
 * @author justburrow
 * @since 2019-01-05
 */
public class VerbPathPapermark implements Papermark {
  private Verb verb;
  private Path path;

  public VerbPathPapermark(Verb verb, Path path) {
    Arguments.notNull(verb, "verb");
    Arguments.notNull(path, "path");

    this.verb = verb;
    this.path = path;
  }

  public Verb getVerb() {
    return this.verb;
  }

  public Path getPath() {
    return this.path;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return String.format("%s %s", this.verb, this.path);
  }
}