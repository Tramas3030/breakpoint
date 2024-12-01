package br.com.Tramas3030.breakpoint.modules.vice.useCase;

import br.com.Tramas3030.breakpoint.exceptions.ViceNotFoundException;
import br.com.Tramas3030.breakpoint.modules.vice.dto.ViceSummaryResponseDTO;
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

  public ViceSummaryResponseDTO execute(Long viceId, UUID userId) {
    Optional<ViceEntity> vice = this.viceRepository.findByIdAndUserId(viceId, userId);

    if(vice.isEmpty()) {
      throw new ViceNotFoundException();
    }

    ViceEntity entity = vice.get();

    return ViceSummaryResponseDTO.builder()
        .id(entity.getId())
        .title(entity.getTitle())
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdated_at())
        .build();
  }
}
