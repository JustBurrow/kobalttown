package kr.lul.kobalttown.configuration.web.support;

import kr.lul.kobalttown.support.spring.security.AccountDetails;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author justburrow
 * @since 2019-03-03
 */
public interface AccountDetailsHandlerMethodArgumentResolver extends HandlerMethodArgumentResolver {
  @Override
  AccountDetails resolveArgument(MethodParameter parameter, ModelAndViewContainer mav, NativeWebRequest request,
      WebDataBinderFactory binderFactory) throws Exception;
}