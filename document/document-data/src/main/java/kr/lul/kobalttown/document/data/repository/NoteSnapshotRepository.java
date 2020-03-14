package kr.lul.kobalttown.document.data.repository;

import kr.lul.kobalttown.document.data.entity.NoteSnapshotEntity;
import kr.lul.kobalttown.document.data.entity.NoteSnapshotEntity.NoteSnapshotId;
import kr.lul.kobalttown.document.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author justburrow
 * @since 2020/03/05
 */
@Repository
public interface NoteSnapshotRepository extends JpaRepository<NoteSnapshotEntity, NoteSnapshotId> {
  List<NoteSnapshotEntity> findAllByNote(Note note);
}
