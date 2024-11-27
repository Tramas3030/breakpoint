package br.com.Tramas3030.breakpoint.modules.user.controllers;

import br.com.Tramas3030.breakpoint.modules.user.dto.AuthUserDTO;
import br.com.Tramas3030.breakpoint.modules.user.useCase.AuthUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthUserController {

  @Autowired
  private AuthUserUseCase authUserUseCase;

  @PostMapping("/user")
  public ResponseEntity<Object> create(@RequestBody AuthUserDTO authUserDTO) {
    try {
      var result = this.authUserUseCase.execute(authUserDTO);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }

}
