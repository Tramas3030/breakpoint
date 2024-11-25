package br.com.Tramas3030.breakpoint.modules.vice.repository;

import br.com.Tramas3030.breakpoint.modules.vice.entities.ViceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ViceRepository extends JpaRepository<ViceEntity, Long> {
  Optional<ViceEntity> findByTitle(String title);
}
