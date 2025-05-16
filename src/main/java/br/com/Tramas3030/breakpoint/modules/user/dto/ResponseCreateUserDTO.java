package br.com.Tramas3030.breakpoint.modules.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

public record ResponseCreateUserDTO(
      UUID id,

      @Schema(example = "Simone Simons")
      String name,

      @Schema(example = "simonesimons@gmail.com")
      String email,

      @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
      @Schema(type = "string", example = "12/05/2025 15:00")
      LocalDateTime createdAt
) {
}