package br.com.Tramas3030.breakpoint.modules.user.controllers;

import br.com.Tramas3030.breakpoint.Providers.JWTProvider;
import br.com.Tramas3030.breakpoint.modules.user.dto.AuthUserDTO;
import br.com.Tramas3030.breakpoint.modules.user.dto.UpdateUserResponseDTO;
import br.com.Tramas3030.breakpoint.modules.user.useCase.AuthUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação do usuário", description = "Rotas relacionadas à autenticação do usuário")
public class AuthUserController {

  @Autowired
  private AuthUserUseCase authUserUseCase;

  @PostMapping("/user")
  @Operation(summary = "Fazer a autenticação do usuário", description = "Esse método é responsável por autenticar o usuário na autenticação, gerando um Token JWT que será usado para fazer as requisições que necessitam de autenticação")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Exemplo de response quando a autenticação é feita com sucesso", content = {
          @Content(mediaType = "text/plain", schema = @Schema(implementation = AuthUserDTO.class), examples = {
              @ExampleObject(
                  name = "Token JWT",
                  summary = "Exemplo de token JWT gerado",
                  value = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
              ),
          })
      }),
      @ApiResponse(responseCode = "400", description = "Erros que podem acontecer na autenticação do usuário", content = {
          @Content(mediaType = "text/plain", examples = {
              @ExampleObject(
                  name = "Usuário não encontrado",
                  summary = "Quando o email fornecido não está cadastrado no sistema",
                  description = "Retornado quando não existe usuário com o email informado",
                  value = "User not found"
              ),
              @ExampleObject(
                  name = "Credenciais inválidas",
                  summary = "Quando a senha fornecida está incorreta",
                  description = "Retornado quando a senha não corresponde à senha cadastrada para o email",
                  value = "Invalid password"
              ),
          })
      })
  })
  public ResponseEntity<Object> create(@RequestBody AuthUserDTO authUserDTO) {
    try {
      var result = this.authUserUseCase.execute(authUserDTO);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }

}
