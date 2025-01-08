package br.com.Tramas3030.breakpoint.modules.vice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViceSummaryListResponseDTO {

  private List<ViceSummaryDTO> allUserVices;

  public boolean isEmpty() {
    return allUserVices.isEmpty();
  }
}
