package br.com.Tramas3030.breakpoint.modules.vice.useCase;

import br.com.Tramas3030.breakpoint.exceptions.ViceNotFoundException;
import br.com.Tramas3030.breakpoint.modules.vice.dto.ViceResponseDTO;
import br.com.Tramas3030.breakpoint.modules.vice.dto.ViceSummaryDTO;
import br.com.Tramas3030.breakpoint.modules.vice.entities.ViceEntity;
import br.com.Tramas3030.breakpoint.modules.vice.repository.ViceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetViceInformationsUseCase {

  @Autowired
  private ViceRepository viceRepository;

  public ViceResponseDTO execute(Long viceId, UUID userId) {
    Optional<ViceEntity> vice = this.viceRepository.findByIdAndUserId(viceId, userId);

    if(vice.isEmpty()) {
      throw new ViceNotFoundException();
    }

    ViceEntity entity = vice.get();

    return ViceResponseDTO.builder()
        .id(entity.getId())
        .title(entity.getTitle())
        .description(entity.getDescription())
        .addictionImpact(entity.getAddictionImpact())
        .impactCost(entity.getImpactCost())
        .criticalHours(entity.getCriticalHours())
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdated_at())
        .build();
  }
}
