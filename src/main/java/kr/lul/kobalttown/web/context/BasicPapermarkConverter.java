package kr.lul.kobalttown.web.context;

import kr.lul.common.util.ConvertException;
import kr.lul.kobalttown.domain.BasicPapermark;
import kr.lul.kobalttown.domain.Papermark;
import org.slf4j.Logger;

import static kr.lul.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * {@link RequestContext}를 {@link BasicPapermark}로 변환.
 *
 * @author justburrow
 * @since 2019-01-05
 */
public class BasicPapermarkConverter implements PapermarkConverter {
  private static final Logger log = getLogger(BasicPapermarkConverter.class);

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.web.context.PapermarkConverter
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Papermark convert(RequestContext requestContext) throws ConvertException {
    if (log.isTraceEnabled()) {
      log.trace("args : requestContext={}", requestContext);
    }
    notNull(requestContext, "requestContext");

    BasicPapermark papermark = new BasicPapermark(requestContext.getVerb(), requestContext.getPath());

    if (log.isTraceEnabled()) {
      log.trace("return : {}", papermark);
    }
    return papermark;
  }
}