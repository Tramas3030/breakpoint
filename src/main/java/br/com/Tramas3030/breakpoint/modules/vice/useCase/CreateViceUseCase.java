package br.com.Tramas3030.breakpoint.modules.vice.useCase;

import br.com.Tramas3030.breakpoint.exceptions.ViceFoundException;
import br.com.Tramas3030.breakpoint.modules.vice.dto.ViceResponseDTO;
import br.com.Tramas3030.breakpoint.modules.vice.dto.ViceSummaryListResponseDTO;
import br.com.Tramas3030.breakpoint.modules.vice.dto.ViceSummaryDTO;
import br.com.Tramas3030.breakpoint.modules.vice.entities.ViceEntity;
import br.com.Tramas3030.breakpoint.modules.vice.repository.ViceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateViceUseCase {

  @Autowired
  private ViceRepository viceRepository;

  public ViceSummaryListResponseDTO execute(ViceEntity viceEntity) {
    this.viceRepository.findByTitleAndUserId(viceEntity.getTitle(), viceEntity.getUserId()).ifPresent(vice -> {
      throw new ViceFoundException();
    });

    viceEntity.setReseted(false);

    this.viceRepository.save(viceEntity);

    List<ViceEntity> allUserVices = this.viceRepository.findAllByUserIdOrderByCreatedAtDesc(viceEntity.getUserId());

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
