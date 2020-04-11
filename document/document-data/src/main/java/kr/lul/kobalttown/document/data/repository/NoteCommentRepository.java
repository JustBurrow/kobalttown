package kr.lul.kobalttown.document.data.repository;

import kr.lul.kobalttown.document.data.entity.NoteCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author justburrow
 * @since 2020/03/17
 */
@Repository
public interface NoteCommentRepository extends JpaRepository<NoteCommentEntity, Long> {
}
