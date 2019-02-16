package kr.lul.kobalttown.loader;

import kr.lul.kobalttown.domain.Paper;
import kr.lul.kobalttown.domain.PaperLoader;
import kr.lul.kobalttown.domain.Verb;
import kr.lul.kobalttown.domain.VerbPathPapermark;

/**
 * 동사와 패스가 일치하는 페이퍼를 찾는 로더.
 *
 * @param <P> 페이퍼 타입.
 *
 * @author justburrow
 * @see Verb 페이퍼 검색 기준.
 * @see java.nio.file.Path 페이퍼 검색 기준.
 * @since 2019-01-05
 */
public interface VerbPathPaperLoader<P extends Paper> extends PaperLoader<VerbPathPapermark, P> {
}