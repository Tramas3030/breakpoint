package br.com.Tramas3030.breakpoint.modules.vice.useCase;

import br.com.Tramas3030.breakpoint.exceptions.*;
import br.com.Tramas3030.breakpoint.modules.vice.dto.UpdateViceDTO;
import br.com.Tramas3030.breakpoint.modules.vice.dto.ViceResponseDTO;
import br.com.Tramas3030.breakpoint.modules.vice.entities.ViceEntity;
import br.com.Tramas3030.breakpoint.modules.vice.repository.ViceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UpdateViceInformationsUseCase {

  @Autowired
  private ViceRepository viceRepository;

  public ViceResponseDTO execute(Long viceId, UUID userId, UpdateViceDTO updateViceDTO) {
    Optional<ViceEntity> viceEntity = this.viceRepository.findByIdAndUserId(viceId, userId);

    if(viceEntity.isEmpty()) {
      throw new ViceNotFoundException();
    }

    ViceEntity vice = viceEntity.get();

    if(updateViceDTO.getDescription() != null && !updateViceDTO.getDescription().isBlank()) {
      if(updateViceDTO.getDescription().equals(vice.getDescription())) {
        throw new EqualViceDescriptionException();
      }

      vice.setDescription(updateViceDTO.getDescription());
    }

    if(updateViceDTO.getAddictionImpact() != null && !updateViceDTO.getAddictionImpact().isBlank()) {
      if(updateViceDTO.getAddictionImpact().equals(vice.getAddictionImpact())) {
        throw new EqualViceAddictionImpactException();
      }

      vice.setAddictionImpact(updateViceDTO.getAddictionImpact());
    }

    if(updateViceDTO.getImpactCost() != null && !updateViceDTO.getImpactCost().isBlank()) {
      if(updateViceDTO.getImpactCost().equals(vice.getImpactCost())) {
        throw new EqualViceImpactCostException();
      }

      vice.setImpactCost(updateViceDTO.getImpactCost());
    }

    if(updateViceDTO.getCriticalHours() != null) {
      Set<String> newCriticalHours = new HashSet<String>(updateViceDTO.getCriticalHours());
      Set<String> currentCriticalHours = new HashSet<String>(vice.getCriticalHours());

      if(newCriticalHours.equals(currentCriticalHours)) {
        throw new EqualViceCriticalHoursException();
      }

      vice.setCriticalHours(updateViceDTO.getCriticalHours());
    }

    vice.setUpdated_at(LocalDateTime.now());

    viceRepository.save(vice);

    return ViceResponseDTO.builder()
        .id(vice.getId())
        .title(vice.getTitle())
        .description(vice.getDescription())
        .addictionImpact(vice.getAddictionImpact())
        .impactCost(vice.getImpactCost())
        .criticalHours(vice.getCriticalHours())
        .createdAt(vice.getCreatedAt())
        .updatedAt(vice.getUpdated_at())
        .reseted(vice.isReseted())
        .build();
  }

}
