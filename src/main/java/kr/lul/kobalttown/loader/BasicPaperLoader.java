package kr.lul.kobalttown.loader;

import kr.lul.kobalttown.domain.BasicPapermark;
import kr.lul.kobalttown.domain.PaperLoader;

/**
 * 동사와 패스가 일치하는 페이퍼를 찾는 로더.
 *
 * @author justburrow
 * @see kr.lul.kobalttown.web.context.Verb 페이퍼 검색 기준.
 * @see java.nio.file.Path 페이퍼 검색 기준.
 * @since 2019-01-05
 */
public interface BasicPaperLoader extends PaperLoader<BasicPapermark> {
}