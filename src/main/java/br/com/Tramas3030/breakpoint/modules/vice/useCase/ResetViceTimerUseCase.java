package br.com.Tramas3030.breakpoint.modules.vice.useCase;

import br.com.Tramas3030.breakpoint.exceptions.ViceNotFoundException;
import br.com.Tramas3030.breakpoint.modules.vice.dto.ViceUpdatedAt;
import br.com.Tramas3030.breakpoint.modules.vice.entities.ViceEntity;
import br.com.Tramas3030.breakpoint.modules.vice.repository.ViceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResetViceTimerUseCase {

  @Autowired
  private ViceRepository viceRepository;

  public ViceUpdatedAt execute(Long viceId, UUID userId) {
    Optional<ViceEntity> viceEntity = this.viceRepository.findByIdAndUserId(viceId, userId);

    if(viceEntity.isEmpty()) {
      throw new ViceNotFoundException();
    }

    ViceEntity vice = viceEntity.get();

    vice.setUpdated_at(LocalDateTime.now());

    viceRepository.save(vice);

    return ViceUpdatedAt.builder()
        .updatedAt(vice.getUpdated_at())
        .build();
  }

}
