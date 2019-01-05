package kr.lul.kobalttown.web.context;

import kr.lul.common.util.Converter;
import kr.lul.kobalttown.domain.Papermark;

/**
 * {@link RequestContext}의 정보를 바탕으로 {@link Papermark}를 생성하는 변환기.
 *
 * @author justburrow
 * @since 2019-01-05
 */
public interface PapermarkConverter extends Converter<RequestContext, Papermark> {
}