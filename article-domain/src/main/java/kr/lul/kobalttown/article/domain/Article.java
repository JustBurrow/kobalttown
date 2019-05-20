package kr.lul.kobalttown.article.domain;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.common.util.Creatable;
import kr.lul.kobalttown.common.util.ValidationException;

import static java.lang.String.format;

/**
 * @author justburrow
 * @since 2019-03-31
 */
public interface Article extends Creatable {
  String ATTR_ID = "id";
  String ATTR_TITLE = "title";
  String ATTR_SUMMARY = "summary";
  String ATTR_BODY = "body";
  String ATTR_CREATOR = "creator";
  String ATTR_CREATED_AT = "createdAt";

  int TITLE_MAX_LENGTH = 20;
  int SUMMARY_MAX_LENGTH = 64;
  int BODY_MAX_LENGTH = 65_535;

  /**
   * 문자열이 제목으로 사용할 수 있는지 확인한다.
   *
   * @param title 제목.
   *
   * @throws ValidationException 검증 실패.
   */
  static void validateTitle(String title) throws ValidationException {
    if (null == title) {
      throw new ValidationException(ATTR_TITLE, "title is null.");
    } else if (title.isEmpty()) {
      throw new ValidationException(ATTR_TITLE, "title is empty.");
    } else if (TITLE_MAX_LENGTH < title.length()) {
      throw new ValidationException(ATTR_TITLE, format("title is too long : length=%d, max=%d",
          title.length(), TITLE_MAX_LENGTH));
    }
  }

  /**
   * 요약으로 사용할 수 있는 문자열인지 확인한다.
   *
   * @param summary 요약.
   *
   * @throws ValidationException 검증 실패.
   */
  static void validateSummary(String summary) throws ValidationException {
    if (null == summary) {
      throw new ValidationException(ATTR_SUMMARY, "summary is null.");
    } else if (summary.isEmpty()) {
      throw new ValidationException(ATTR_SUMMARY, "summary is empty.");
    } else if (SUMMARY_MAX_LENGTH < summary.length()) {
      throw new ValidationException(ATTR_SUMMARY,
          format("summary is too long : length=%d, max=%d", summary.length(), SUMMARY_MAX_LENGTH));
    }
  }

  /**
   * 본문으로 사용할 수 있는 문자열인지 확인한다.
   *
   * @param body 본문.
   *
   * @throws ValidationException 검증 실패.
   */
  static void validateBody(String body) throws ValidationException {
    if (null == body) {
      throw new ValidationException(ATTR_BODY, "body is null.");
    } else if (body.isEmpty()) {
      throw new ValidationException(ATTR_BODY, "body is empty.");
    }
  }

  /**
   * @return ID
   */
  long getId();

  /**
   * @return 제목.
   */
  String getTitle();

  /**
   * @return 요약.
   */
  String getSummary();

  /**
   * @return 본문.
   */
  String getBody();

  /**
   * @return 작성자.
   */
  @Deprecated
  default Account getAuthor() {
    return getCreator();
  }

  /**
   * @return 작성자.
   */
  Account getCreator();
}