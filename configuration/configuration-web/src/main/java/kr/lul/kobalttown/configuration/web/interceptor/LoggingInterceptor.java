package kr.lul.kobalttown.configuration.web.interceptor;

import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.configuration.bean.context.ContextService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.time.Instant;
import java.util.Map;

import static java.time.Duration.between;
import static java.util.Collections.list;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toMap;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/28
 */
public class LoggingInterceptor implements HandlerInterceptor {
  private static final Logger log = getLogger(LoggingInterceptor.class);

  @Autowired
  private ContextService contextService;
  @Autowired
  private TimeProvider timeProvider;

  private ThreadLocal<Instant> pre;

  @PostConstruct
  private void postConstruct() {
    requireNonNull(this.contextService, "uuidContextService is not autowired.");
    requireNonNull(this.timeProvider, "timeProvider is not autowired.");

    this.pre = new ThreadLocal<>();
    log.info("{} is ready.", LoggingInterceptor.class);
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    if (log.isTraceEnabled())
      log.trace("#preHandle args : request={}, response={}, handler={}", request, response, handler);

    final Instant now = this.timeProvider.now();
    this.pre.set(now);

    String method = request.getMethod();
    URL url = new URL(request.getRequestURL().toString());
    Map<String, String> headers = list(request.getHeaderNames()).stream()
        .collect(toMap(name -> name, request::getHeader));

    if (log.isInfoEnabled())
      log.info("#preHandle context={}, timestamp={}, method={}, url={}, headers={}",
          this.contextService.issue(), now, method, url, headers);

    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex
  ) throws Exception {
    if (log.isTraceEnabled())
      log.trace("#afterCompletion args : request={}, response={}, handler={}, ex={}", request, response, handler, ex);
    final Instant now = this.timeProvider.now();

    Map<String, String> headers = response.getHeaderNames().stream()
        .collect(toMap(name -> name, response::getHeader));

    if (log.isInfoEnabled())
      log.info("#afterCompletion context={}, timestamp={}, latency={}, headers={}",
          this.contextService.get(), now, between(this.pre.get(), now), headers);

    this.pre.remove();
    this.contextService.clear();
  }
}
