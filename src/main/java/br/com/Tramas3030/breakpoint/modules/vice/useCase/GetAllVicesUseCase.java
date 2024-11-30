package br.com.Tramas3030.breakpoint.modules.vice.useCase;

import br.com.Tramas3030.breakpoint.modules.vice.dto.ViceListResponseDTO;
import br.com.Tramas3030.breakpoint.modules.vice.dto.ViceSummaryResponseDTO;
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

  public ViceListResponseDTO execute(UUID userId) {
    List<ViceEntity> allUserVices = this.viceRepository.findAllByUserId(userId);

    List<ViceSummaryResponseDTO> viceSummaryList = allUserVices.stream()
        .map(vice -> ViceSummaryResponseDTO.builder()
            .id(vice.getId())
            .title(vice.getTitle())
            .createdAt(vice.getCreatedAt())
            .updatedAt(vice.getUpdated_at())
            .build())
        .toList();

    return ViceListResponseDTO.builder()
        .allUserVices(viceSummaryList)
        .build();
  }
}
