package br.com.Tramas3030.breakpoint.modules.vice.useCase;

import br.com.Tramas3030.breakpoint.modules.vice.dto.ViceSummaryListResponseDTO;
import br.com.Tramas3030.breakpoint.modules.vice.dto.ViceSummaryDTO;
import br.com.Tramas3030.breakpoint.modules.vice.entities.ViceEntity;
import br.com.Tramas3030.breakpoint.modules.vice.repository.ViceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetAllVicesUseCase {

  @Autowired
  private ViceRepository viceRepository;

  public ViceSummaryListResponseDTO execute(UUID userId) {
    List<ViceEntity> allUserVices = this.viceRepository.findAllByUserIdOrderById(userId);

    List<ViceSummaryDTO> viceSummaryList = allUserVices.stream()
        .map(vice -> ViceSummaryDTO.builder()
            .id(vice.getId())
            .title(vice.getTitle())
            .createdAt(vice.getCreatedAt())
            .updatedAt(vice.getUpdated_at())
            .reseted(vice.isReseted())
            .build())
        .toList();

    return ViceSummaryListResponseDTO.builder()
        .allUserVices(viceSummaryList)
        .build();
  }
}
