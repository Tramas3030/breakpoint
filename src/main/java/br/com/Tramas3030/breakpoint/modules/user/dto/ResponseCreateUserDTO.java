package br.com.Tramas3030.breakpoint.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCreateUserDTO {

  private UUID id;
  @Schema(example = "Simone Simons")
  private String name;
  @Schema(example = "simonesimons@gmail.com")
  private String email;
  private LocalDateTime createdAt;

}
