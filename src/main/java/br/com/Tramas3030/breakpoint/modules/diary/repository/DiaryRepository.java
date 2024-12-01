package br.com.Tramas3030.breakpoint.modules.diary.repository;

import br.com.Tramas3030.breakpoint.modules.diary.entities.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {
  List<DiaryEntity> findAllByUserId(UUID userId);
  Optional<DiaryEntity> findByIdAndUserId(Long id, UUID UserId);
}
