package kr.lul.kobalttown.article.domain;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.common.util.Creatable;

/**
 * @author justburrow
 * @since 2019-03-31
 */
public interface Article extends Creatable {
  int SUMMARY_MAX_LENGTH = 20;

  long getId();

  String getTitle();

  String getSummary();

  String getBody();

  default Account getAuthor() {
    return getCreator();
  }

  Account getCreator();
}