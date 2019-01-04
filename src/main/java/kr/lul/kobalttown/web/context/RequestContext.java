package kr.lul.kobalttown.web.context;

import java.nio.file.Path;

/**
 * @author justburrow
 * @since 2018-12-25
 */
public interface RequestContext {
  /**
   * @return
   */
  Verb getVerb();

  Path getPath();
}