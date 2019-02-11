package kr.lul.kobalttown.web.support;

import kr.lul.kobalttown.domain.Paper;
import kr.lul.kobalttown.web.context.RequestContext;
import kr.lul.kobalttown.web.context.Verb;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.StringJoiner;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notEmpty;
import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2018-12-25
 */
public class ServletRequestContext implements RequestContext {
  private Verb verb;
  private URL url;

  private ModelAndViewContainer mav;
  private ServletWebRequest request;
  private WebDataBinderFactory binderFactory;

  public ServletRequestContext(ModelAndViewContainer mav, ServletWebRequest request, WebDataBinderFactory binderFactory
  ) {
    notNull(mav, "mav");
    notNull(request, "request");
    notNull(binderFactory, "binderFactory");

    this.mav = mav;
    this.request = request;
    this.binderFactory = binderFactory;

    init();
  }

  private void init() {
    this.verb = Verb.byAlias(this.request.getHttpMethod().name());
    if (null == this.verb) {
      throw new IllegalArgumentException(format("unsupported HTTP method : %s", this.request.getHttpMethod()));
    }

    // URL
    try {
      this.url = new URL(this.request.getRequest().getRequestURL().toString());
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException(e);
    }
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
    return Path.of(this.url.getPath());
  }

  @Override
  public ModelMap getModel() {
    return this.mav.getModel();
  }

  @Override
  public void setViewname(String view) {
    this.mav.setViewName(view);
  }

  @Override
  public String getViewname() {
    return this.mav.getViewName();
  }

  @Override
  public void setPaper(Paper paper) {
    notNull(paper, "paper");

    setViewname(format(THEME_LAYOUT_FORMAT, paper.getTheme()));

    this.mav.addAttribute(ATTR_PAPER, paper);
  }

  @Override
  public void addModelAttribute(String name, Object attribute) {
    notEmpty(name, "name");
    notNull(attribute, "attribute");

    if (RESERVED_ATTRIBUTE_NAMES.contains(name)) {
      throw new IllegalArgumentException(format("reserved attribute name : '%s'", name));
    }
    if (this.mav.containsAttribute(name)) {
      throw new IllegalArgumentException(format("attribute already exists : name='%s', old=%s, new=%s",
          name, this.mav.getModel().get(name), attribute));
    }

    this.mav.addAttribute(name, attribute);
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return new StringJoiner(", ", ServletRequestContext.class.getSimpleName() + "{", "}")
        .add("verb=" + this.verb)
        .add("url=" + this.url)
        .add("mav=" + this.mav)
        .add("request=" + this.request)
        .add("binderFactory=" + this.binderFactory)
        .toString();
  }
}