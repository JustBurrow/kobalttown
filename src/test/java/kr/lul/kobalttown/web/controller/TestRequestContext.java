package kr.lul.kobalttown.web.controller;

import kr.lul.kobalttown.domain.Paper;
import kr.lul.kobalttown.web.context.RequestContext;
import kr.lul.kobalttown.web.context.Verb;
import org.springframework.ui.ModelMap;

import java.nio.file.Path;
import java.util.StringJoiner;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2019-01-05
 */
public class TestRequestContext implements RequestContext {
  private Verb verb;
  private Path path;
  private ModelMap modelMap;
  private String viewname;

  public TestRequestContext(Verb verb, Path path) {
    this(verb, path, new ModelMap());
  }

  public TestRequestContext(Verb verb, Path path, ModelMap modelMap) {
    this(verb, path, new ModelMap(), null);
  }

  public TestRequestContext(Verb verb, Path path, ModelMap modelMap, String viewname) {
    notNull(verb, "verb");
    notNull(path, "path");
    notNull(modelMap, "modelMap");

    this.verb = verb;
    this.path = path;
    this.modelMap = modelMap;
    this.viewname = viewname;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.web.context.RequestContext
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Verb getVerb() {
    return this.verb;
  }

  @Override
  public Path getPath() {
    return this.path;
  }

  @Override
  public ModelMap getModel() {
    return this.modelMap;
  }

  @Override
  public void setViewname(String view) {
    this.viewname = view;
  }

  @Override
  public String getViewname() {
    return this.viewname;
  }

  @Override
  public void setPaper(Paper paper) {
    notNull(paper, "paper");

    setViewname(format(THEME_LAYOUT_FORMAT, paper.getTheme()));
  }

  @Override
  public void addModelAttribute(String name, Object attribute) {
    this.modelMap.addAttribute(name, attribute);
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return new StringJoiner(", ", TestRequestContext.class.getSimpleName() + "[", "]")
        .add("verb=" + this.verb)
        .add("path=" + this.path)
        .add("modelMap=" + this.modelMap)
        .add("viewname=" + singleQuote(this.viewname))
        .toString();
  }
}