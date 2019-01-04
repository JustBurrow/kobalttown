package kr.lul.kobalttown.web.support;

import kr.lul.kobalttown.web.context.RequestContext;
import kr.lul.kobalttown.web.context.Verb;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.StringJoiner;

import static java.lang.String.format;
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
    // Verb
    switch (this.request.getHttpMethod()) {
      case POST:
        this.verb = Verb.CREATE;
        break;
      case GET:
        this.verb = Verb.READ;
        break;
      case PATCH:
        this.verb = Verb.UPDATE;
        break;
      case DELETE:
        this.verb = Verb.DELETE;
        break;
      default:
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
    try {
      return Path.of(this.url.toURI());
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
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