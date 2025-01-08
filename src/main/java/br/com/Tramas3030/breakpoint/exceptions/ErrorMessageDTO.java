package br.com.Tramas3030.breakpoint.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessageDTO {

  @Schema(example = "A senha deve conter entre (5) e (100) caracteres")
  public String message;
  @Schema(example = "password")
  public String field;

}
