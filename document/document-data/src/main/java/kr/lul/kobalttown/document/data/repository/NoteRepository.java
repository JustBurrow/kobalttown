package kr.lul.kobalttown.document.data.repository;

import kr.lul.kobalttown.document.data.entity.NoteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author justburrow
 * @since 2020/02/07
 */
@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
  Page<NoteEntity> findByDeletedAtIsNull(Pageable pageable);
}
