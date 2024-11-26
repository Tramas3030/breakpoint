package br.com.Tramas3030.breakpoint.modules.vice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViceSummaryResponseDTO {

  private long id;
  private String title;
  private LocalDateTime createdAt;

}
