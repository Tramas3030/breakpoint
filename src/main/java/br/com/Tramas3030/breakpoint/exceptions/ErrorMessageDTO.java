package br.com.Tramas3030.breakpoint.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessageDTO {

  public String message;
  public String field;

}
