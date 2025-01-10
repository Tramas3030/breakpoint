package br.com.Tramas3030.breakpoint.modules.user.controllers;

import br.com.Tramas3030.breakpoint.exceptions.ErrorMessageDTO;
import br.com.Tramas3030.breakpoint.modules.user.dto.ResponseCreateUserDTO;
import br.com.Tramas3030.breakpoint.modules.user.dto.UpdateUserResponseDTO;
import br.com.Tramas3030.breakpoint.modules.user.dto.UserInformationsDTO;
import br.com.Tramas3030.breakpoint.modules.user.entities.UserEntity;
import br.com.Tramas3030.breakpoint.modules.user.useCase.CreateUserUseCase;
import br.com.Tramas3030.breakpoint.modules.user.useCase.DeleteUserUseCase;
import br.com.Tramas3030.breakpoint.modules.user.useCase.GetUserInformationsUseCase;
import br.com.Tramas3030.breakpoint.modules.user.useCase.UpdateUserInformationsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@Tag(name = "Usuário", description = "Rotas relacionadas ao usuário")
public class UserController {

  @Autowired
  private CreateUserUseCase createUserUseCase;

  @Autowired
  private GetUserInformationsUseCase getUserInformationsUseCase;

  @Autowired
  private UpdateUserInformationsUseCase updateUserInformationsUseCase;

  @Autowired
  private DeleteUserUseCase deleteUserUseCase;

  @PostMapping("/")
  @Operation(summary = "Criação de um novo usuário", description = "Esse método é resposável pela criação de um novo usuário na aplicação")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Exemplo de response quando o usuário é criado com sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseCreateUserDTO.class))
      }),
      @ApiResponse(
          responseCode = "400",
          description = "Erros possíveis: 1) Usuário já existe na aplicação (email já cadastrado) ou 2) Campos inválidos",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(oneOf = {ErrorMessageDTO.class, String.class}),
                  examples = {
                      @ExampleObject(
                          name = "Usuário já existe",
                          summary = "Quando o email já está cadastrado",
                          description = "Retorna uma mensagem de erro simples",
                          value = "User already exists"
                      ),
                      @ExampleObject(
                          name = "Campo inválido",
                          summary = "Quando um dos campos é inválido",
                          description = "Retorna o campo e a mensagem de erro",
                          value = """
                              {
                                "message": "A senha deve conter entre (5) e (100) caracteres",
                                "field": "password"
                              }
                              """
                      )
                  }
              )
          }
      )
  })
  public ResponseEntity<Object> create(@Valid @RequestBody UserEntity userEntity) {
    try {
      var result = createUserUseCase.execute(userEntity);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/")
  @Operation(summary = "Obter as informações de um usuário", description = "Esse método é responsável por obter as informações de um usuário cadastrado na aplicação")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Exemplo de response quando pegamos a informação do usuário", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = UserInformationsDTO.class))
      }),
      @ApiResponse(
          responseCode = "400",
          description = "Erros possíveis: 1) Passar um JWT Token inválido ou 2) Passar um JWT de um usuário que não existe na aplicação",
          content = {
              @Content(
                  mediaType = "text/plain",
                  examples = {
                      @ExampleObject(
                          name = "Token JWT inválido",
                          summary = "Quando o token JWT fornecido é inválido.",
                          value = "Invalid UUID string:"
                      ),
                      @ExampleObject(
                          name = "Usuário não encontrado",
                          summary = "Quando o ID do usuário no JWT não corresponde a um usuário existente.",
                          description = "Retorna uma mensagem de erro informando que o usuário não foi encontrado.",
                          value = "User not found"
                      )
                  }
              )
          }
      )
  })
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> getUserInformations(HttpServletRequest request) {
    var userId = request.getAttribute("user_id");

    try {
      var result = getUserInformationsUseCase.execute(UUID.fromString(userId.toString()));
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/")
  @Operation(summary = "Atualizar as informações de um usuário", description = "Esse método é responsável por atualizar as informações de um usuário cadastrado na aplicação. Pode atualizar tanto os três campos abaixo quanto apenas um deles")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Exemplo de response quando é feito com sucesso a atualização de alguma informação do usuário", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateUserResponseDTO.class))
      }),
      @ApiResponse(
          responseCode = "400",
          description = "Erros possíveis ao atualizar informações do usuário",
          content = {
              @Content(
                  mediaType = "text/plain",
                  examples = {
                      @ExampleObject(
                          name = "Usuário não encontrado",
                          summary = "Quando o ID do usuário no JWT não corresponde a um usuário existente",
                          value = "User not found"
                      ),
                      @ExampleObject(
                          name = "Email já em uso",
                          summary = "Quando tenta atualizar para um email que já está sendo usado pelo próprio usuário",
                          value = "This email is already in use"
                      ),
                      @ExampleObject(
                          name = "Nome já em uso",
                          summary = "Quando tenta atualizar para um nome que já está sendo usado pelo próprio usuário",
                          value = "This name is already in use"
                      ),
                      @ExampleObject(
                          name = "Senha inválida",
                          summary = "Quando a nova senha não atende aos requisitos de tamanho (5-100 caracteres)",
                          value = "Password must be between 5 and 100 characters"
                      ),
                      @ExampleObject(
                          name = "Senha já em uso",
                          summary = "Quando tenta atualizar para uma senha que já está sendo usada pelo usuário",
                          value = "This password is already in use"
                      ),
                      @ExampleObject(
                          name = "UUID inválido",
                          summary = "Quando o token JWT contém um UUID em formato inválido",
                          value = "Invalid UUID string:"
                      )
                  }
              )
          }
      ),
      @ApiResponse(responseCode = "403", description = "Quando o token jwt não for passado, o resultado será um 403 forbidden")
  })
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> update(HttpServletRequest request, @RequestBody UserEntity userEntity) {
    var userId = request.getAttribute("user_id");

    try {
      var result = updateUserInformationsUseCase.execute(UUID.fromString(userId.toString()), userEntity);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/")
  @Operation(summary = "Deletar um usuário", description = "Esse método é responsável por deletar um usuário da aplicação. Quando um usuário é deletado, automaticamente os vícios que ele cadastrou e as notas do diário que ele criou são deletadas também.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Response de quando o usuário é deletado com sucesso", content = {
          @Content(mediaType = "text/plain", examples = {
              @ExampleObject(
                  value = "User successfully deleted"
              )
          })
      }),
      @ApiResponse(responseCode = "400", description = "Quando o usuário não for encontrado", content = {
          @Content(mediaType = "text/plain", examples = {
              @ExampleObject(
                  value = "User not found"
              )
          })
      }),
      @ApiResponse(responseCode = "403", description = "Quando o token jwt não for passado, o resultado será um 403 forbidden")

  })
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> delete(HttpServletRequest request) {
    var userId = request.getAttribute("user_id");

    try {
      boolean deleted = this.deleteUserUseCase.execute(UUID.fromString(userId.toString()));

      return ResponseEntity.ok().body("User successfully deleted");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
