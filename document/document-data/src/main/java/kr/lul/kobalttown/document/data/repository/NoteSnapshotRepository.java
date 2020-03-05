package kr.lul.kobalttown.document.data.repository;

import kr.lul.kobalttown.document.data.entity.NoteSnapshotEntity;
import kr.lul.kobalttown.document.data.entity.NoteSnapshotEntity.NoteSnapshotId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author justburrow
 * @since 2020/03/05
 */
@Repository
public interface NoteSnapshotRepository extends JpaRepository<NoteSnapshotEntity, NoteSnapshotId> {
}
