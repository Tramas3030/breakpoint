package br.com.Tramas3030.breakpoint.modules.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInformationsDTO {

  private String name;
  private String email;
  private LocalDateTime createdAt;

}
