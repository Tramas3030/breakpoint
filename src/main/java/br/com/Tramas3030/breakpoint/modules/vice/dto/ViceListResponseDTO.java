package br.com.Tramas3030.breakpoint.modules.vice.dto;

import br.com.Tramas3030.breakpoint.modules.vice.entities.ViceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViceListResponseDTO {

  private List<ViceSummaryResponseDTO> allUserVices;

}
