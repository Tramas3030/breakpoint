package br.com.Tramas3030.breakpoint.modules.user.controllers;

import br.com.Tramas3030.breakpoint.exceptions.ErrorMessageDTO;
import br.com.Tramas3030.breakpoint.modules.user.dto.ResponseCreateUserDTO;
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
      @ApiResponse(responseCode = "400", description = "Exemplo de response quando o usuário já existe na aplicação (quando o email dele já está cadastrado)", content = {
          @Content(mediaType = "text/html", examples = {
              @ExampleObject(value = "User already exists")
          })
      })
      /*@ApiResponse(responseCode = "400", description = "Exemplo de response quando um dos campos name, email ou senha são inválidos", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageDTO.class))
      })*/
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
