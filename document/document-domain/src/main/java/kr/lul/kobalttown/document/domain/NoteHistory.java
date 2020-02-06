package kr.lul.kobalttown.document.domain;

import java.util.List;

/**
 * @author justburrow
 * @since 2020/02/06
 */
public interface NoteHistory extends History {
  @Override
  @SuppressWarnings("unchecked")
  List<NoteSnapshot> content();
}
