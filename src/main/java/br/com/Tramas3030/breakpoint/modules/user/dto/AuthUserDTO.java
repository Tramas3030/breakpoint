package br.com.Tramas3030.breakpoint.modules.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthUserDTO {

  private String email;
  private String password;

}