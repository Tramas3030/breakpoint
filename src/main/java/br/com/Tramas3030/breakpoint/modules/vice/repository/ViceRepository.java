package br.com.Tramas3030.breakpoint.modules.vice.repository;

import br.com.Tramas3030.breakpoint.modules.vice.entities.ViceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ViceRepository extends JpaRepository<ViceEntity, Long> {
  Optional<ViceEntity> findByTitleAndUserId(String title, UUID UserId);
  List<ViceEntity> findAllByUserId(UUID userId);
  Optional<ViceEntity> findByIdAndUserId(Long id, UUID UserId);
}
