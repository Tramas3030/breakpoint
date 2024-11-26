package br.com.Tramas3030.breakpoint.modules.vice.useCase;

import br.com.Tramas3030.breakpoint.exceptions.ViceFoundException;
import br.com.Tramas3030.breakpoint.modules.vice.dto.ViceListResponseDTO;
import br.com.Tramas3030.breakpoint.modules.vice.dto.ViceSummaryResponseDTO;
import br.com.Tramas3030.breakpoint.modules.vice.entities.ViceEntity;
import br.com.Tramas3030.breakpoint.modules.vice.repository.ViceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreateViceUseCase {

  @Autowired
  private ViceRepository viceRepository;

  public ViceListResponseDTO execute(ViceEntity viceEntity) {
    this.viceRepository.findByTitleAndUserId(viceEntity.getTitle(), viceEntity.getUserId()).ifPresent(vice -> {
      throw new ViceFoundException();
    });

    this.viceRepository.save(viceEntity);

    List<ViceEntity> allUserVices = this.viceRepository.findAllByUserId(viceEntity.getUserId());

    List<ViceSummaryResponseDTO> viceSummaryList = allUserVices.stream()
        .map(vice -> ViceSummaryResponseDTO.builder()
            .id(vice.getId())
            .title(vice.getTitle())
            .createdAt(vice.getCreatedAt())
            .build())
        .toList();

    return ViceListResponseDTO.builder()
        .allUserVices(viceSummaryList)
        .build();
  }
}
