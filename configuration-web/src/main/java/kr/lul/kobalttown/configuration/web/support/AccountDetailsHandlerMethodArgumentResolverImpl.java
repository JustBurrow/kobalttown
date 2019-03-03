package kr.lul.kobalttown.configuration.web.support;

import kr.lul.kobalttown.support.spring.security.AccountDetails;
import org.slf4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-03-03
 */
public class AccountDetailsHandlerMethodArgumentResolverImpl implements AccountDetailsHandlerMethodArgumentResolver {
  private static final Logger log = getLogger(AccountDetailsHandlerMethodArgumentResolverImpl.class);

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(AccountDetails.class);
  }

  @Override
  public AccountDetails resolveArgument(MethodParameter parameter, ModelAndViewContainer mav, NativeWebRequest request,
      WebDataBinderFactory binderFactory) throws Exception {
    if (log.isTraceEnabled()) {
      log.trace("args : parameter={}, mav={}, request={}, binderFactory={}", parameter, mav, request, binderFactory);
    }

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Object principal = authentication.getPrincipal();
    final AccountDetails user = principal instanceof AccountDetails
        ? (AccountDetails) principal
        : null;

    if (log.isTraceEnabled()) {
      log.trace("return : {}", user);
    }
    return user;
  }
}