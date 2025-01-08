package br.com.Tramas3030.breakpoint.modules.vice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViceResponseDTO {

  private long id;
  private String title;
  private String description;
  private String addictionImpact;
  private String impactCost;
  private List<String> criticalHours;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}
