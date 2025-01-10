package br.com.Tramas3030.breakpoint.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserResponseDTO {

  @Schema(example = "Simone Simons")
  private String name;
  @Schema(example = "simonesimons@gmail.com")
  private String email;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}
