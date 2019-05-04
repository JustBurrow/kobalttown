package kr.lul.kobalttown.article.borderline;

import kr.lul.kobalttown.article.borderline.command.CreateArticleCmd;
import kr.lul.kobalttown.article.domain.CreateArticleException;
import kr.lul.kobalttown.article.dto.DetailArticleDto;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author justburrow
 * @since 2019-04-25
 */
@Transactional
public interface ArticleBorderline {
  /**
   * 신규 아티클을 작성한다.
   *
   * @param cmd 신규 아티클 정보.
   *
   * @return 신규 작성된 아티클.
   *
   * @throws CreateArticleException 아티클 작성 실패한 경우.
   */
  DetailArticleDto create(CreateArticleCmd cmd) throws CreateArticleException;
}