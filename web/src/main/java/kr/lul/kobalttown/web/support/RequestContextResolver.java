package kr.lul.kobalttown.web.support;

import kr.lul.kobalttown.web.context.RequestContext;
import org.slf4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2018-12-25
 */
public class RequestContextResolver implements HandlerMethodArgumentResolver {
  private static final Logger log = getLogger(RequestContextResolver.class);

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // org.springframework.web.method.support.HandlerMethodArgumentResolver
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    if (log.isTraceEnabled()) {
      log.trace("args : parameter={}", parameter);
    }

    boolean supports = RequestContext.class.isAssignableFrom(parameter.getParameterType());

    if (log.isTraceEnabled()) {
      log.trace("return : {}", supports);
    }
    return supports;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mav,
      NativeWebRequest request, WebDataBinderFactory binderFactory
  ) throws Exception {
    if (log.isTraceEnabled()) {
      log.trace("args : parameter={}, mav={}, request={}, binderFactory={}", parameter, mav, request, binderFactory);
    }

    ServletRequestContext context;
    if (request instanceof ServletWebRequest) {
      context = new ServletRequestContext(mav, (ServletWebRequest) request, binderFactory);
    } else {
      throw new IllegalArgumentException(format("unsupported request type : %s", request.getClass()));
    }

    if (log.isTraceEnabled()) {
      log.trace("return : {}", context);
    }
    return context;
  }
}