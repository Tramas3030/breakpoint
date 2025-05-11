package br.com.Tramas3030.breakpoint.modules.vice.controllers;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.Tramas3030.breakpoint.modules.vice.dto.UpdateViceDTO;
import br.com.Tramas3030.breakpoint.modules.vice.dto.ViceResponseDTO;
import br.com.Tramas3030.breakpoint.modules.vice.dto.ViceSummaryListResponseDTO;
import br.com.Tramas3030.breakpoint.modules.vice.dto.ViceUpdatedAt;
import br.com.Tramas3030.breakpoint.modules.vice.entities.ViceEntity;
import br.com.Tramas3030.breakpoint.modules.vice.useCase.CreateViceUseCase;
import br.com.Tramas3030.breakpoint.modules.vice.useCase.DeleteViceUseCase;
import br.com.Tramas3030.breakpoint.modules.vice.useCase.GetAllVicesUseCase;
import br.com.Tramas3030.breakpoint.modules.vice.useCase.GetViceInformationsUseCase;
import br.com.Tramas3030.breakpoint.modules.vice.useCase.ResetViceTimerUseCase;
import br.com.Tramas3030.breakpoint.modules.vice.useCase.UpdateViceInformationsUseCase;
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

@RestController
@RequestMapping("/user/vice")
@Tag(name = "Vícios", description = "Rotas relacionadas aos vícios")
@SecurityRequirement(name = "jwt_auth")
public class ViceController {

  @Autowired
  private GetAllVicesUseCase getAllVicesUseCase;

  @Autowired
  private GetViceInformationsUseCase getViceInformationsUseCase;

  @Autowired
  private CreateViceUseCase createViceUseCase;

  @Autowired
  private DeleteViceUseCase deleteViceUseCase;

  @Autowired
  private ResetViceTimerUseCase resetViceTimerUseCase;

  @Autowired
  private UpdateViceInformationsUseCase updateViceInformationsUseCase;

  @GetMapping("/")
  @Operation(summary = "Listar todos os vícios do usuário", description = "Esse método é responsável por listar todos os vícios cadastrados pelo usuário na aplicação")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Exemplo de resposta quando o usuário possui vícios cadastrados", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ViceSummaryListResponseDTO.class))
      }),
      @ApiResponse(responseCode = "200", description = "Exemplo de resposta quando o usuário não possui vícios cadastrados", content = {
          @Content(mediaType = "text/plain", examples = {
              @ExampleObject(
                  value = "You do not have any registered addiction."
              )
          })
      }),
      @ApiResponse(responseCode = "403", description = "Quando o token JWT não for passado, o resultado será um 403 forbidden")
  })
  public ResponseEntity<Object> listAllUserVices(HttpServletRequest request) {
    var userId = request.getAttribute("user_id");

    ViceSummaryListResponseDTO result = getAllVicesUseCase.execute(UUID.fromString(userId.toString()));

    if(result.isEmpty()) {
      return ResponseEntity.ok().body("You do not have any registered addiction.");
    }

    return ResponseEntity.ok().body(result.getAllUserVices());
  }

  @GetMapping("/{viceId}")
  @Operation(summary = "Obter informações de um vício específico", description = "Esse método é responsável por obter todas as informações de um vício específico do usuário")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Exemplo de resposta quando as informações do vício são encontradas com sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ViceResponseDTO.class))
      }),
      @ApiResponse(responseCode = "400", description = "Erros possíveis: 1) Vício não encontrado ou 2) O vício não pertence ao usuário", content = {
          @Content(mediaType = "text/plain", examples = {
              @ExampleObject(
                  name = "Vício não encontrado",
                  summary = "Quando o ID do vício não corresponde a um registro existente",
                  value = "Vice not found"
              ),
              @ExampleObject(
                  name = "Vício não pertence ao usuário",
                  summary = "Quando o vício não pertence ao usuário autenticado",
                  value = "You are not allowed to access this addiction"
              )
          })
      }),
      @ApiResponse(responseCode = "403", description = "Quando o token JWT não for passado, o resultado será um 403 forbidden")
  })
  public ResponseEntity<Object> getViceInformations(@PathVariable Long viceId, HttpServletRequest request) {
    var userId = request.getAttribute("user_id");

    try {
      ViceResponseDTO result = this.getViceInformationsUseCase.execute(viceId, UUID.fromString(userId.toString()));

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{viceId}/reset")
  @Operation(summary = "Resetar o timer de um vício", description = "Esse método é responsável por resetar o cronômetro de abstinência de um vício específico")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Exemplo de resposta quando o timer do vício é resetado com sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ViceUpdatedAt.class))
      }),
      @ApiResponse(responseCode = "400", description = "Erros possíveis: 1) Vício não encontrado ou 2) O vício não pertence ao usuário", content = {
          @Content(mediaType = "text/plain", examples = {
              @ExampleObject(
                  name = "Vício não encontrado",
                  summary = "Quando o ID do vício não corresponde a um registro existente",
                  value = "Vice not found"
              ),
              @ExampleObject(
                  name = "Vício não pertence ao usuário",
                  summary = "Quando o vício não pertence ao usuário autenticado",
                  value = "You are not allowed to reset this addiction"
              )
          })
      }),
      @ApiResponse(responseCode = "403", description = "Quando o token JWT não for passado, o resultado será um 403 forbidden")
  })
  public ResponseEntity<Object> resetViceTimer(@PathVariable Long viceId, HttpServletRequest request) {
    var userId = request.getAttribute("user_id");

    try {
      ViceUpdatedAt result = this.resetViceTimerUseCase.execute(viceId, UUID.fromString(userId.toString()));

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{viceId}/update")
  @Operation(summary = "Atualizar informações de um vício", description = "Esse método é responsável por atualizar as informações de um vício específico. Pode atualizar um ou mais campos")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Exemplo de resposta quando as informações do vício são atualizadas com sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ViceResponseDTO.class))
      }),
      @ApiResponse(responseCode = "400", description = "Erros possíveis: 1) Vício não encontrado ou 2) O vício não pertence ao usuário", content = {
          @Content(mediaType = "text/plain", examples = {
              @ExampleObject(
                  name = "Vício não encontrado",
                  summary = "Quando o ID do vício não corresponde a um registro existente",
                  value = "Vice not found"
              ),
              @ExampleObject(
                  name = "Vício não pertence ao usuário",
                  summary = "Quando o vício não pertence ao usuário autenticado",
                  value = "You are not allowed to update this addiction"
              )
          })
      }),
      @ApiResponse(responseCode = "403", description = "Quando o token JWT não for passado, o resultado será um 403 forbidden")
  })
  public ResponseEntity<Object> changeViceInformations(@PathVariable Long viceId, HttpServletRequest request, @RequestBody UpdateViceDTO updateViceDTO) {
    var userId = request.getAttribute("user_id");

    try {
      ViceResponseDTO result = this.updateViceInformationsUseCase.execute(viceId, UUID.fromString(userId.toString()), updateViceDTO);

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/")
  @Operation(summary = "Criar um novo vício", description = "Esse método é responsável por cadastrar um novo vício para o usuário")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Exemplo de resposta quando o vício é criado com sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ViceSummaryListResponseDTO.class))
      }),
      @ApiResponse(responseCode = "400", description = "Erros possíveis: 1) Campo título não preenchido ou 2) Erro interno ao criar o vício", content = {
          @Content(mediaType = "text/plain", examples = {
              @ExampleObject(
                  name = "Título não preenchido",
                  summary = "Quando o campo obrigatório 'title' não é fornecido",
                  value = "O campo [title] não pode ser vazio"
              ),
              @ExampleObject(
                  name = "Erro interno",
                  summary = "Quando ocorre um erro interno ao processar a requisição",
                  value = "Erro ao criar o vício"
              )
          })
      }),
      @ApiResponse(responseCode = "403", description = "Quando o token JWT não for passado, o resultado será um 403 forbidden")
  })
  public ResponseEntity<Object> create(@Valid @RequestBody ViceEntity viceEntity, HttpServletRequest request) {
    try {
      var userId = request.getAttribute("user_id");
      viceEntity.setUserId(UUID.fromString(userId.toString()));

      ViceSummaryListResponseDTO result = this.createViceUseCase.execute(viceEntity);
      return ResponseEntity.ok().body(result.getAllUserVices());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{viceId}")
  @Operation(summary = "Deletar um vício", description = "Esse método é responsável por deletar um vício específico do usuário")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Resposta quando o vício é excluído com sucesso", content = {
          @Content(mediaType = "text/plain", examples = {
              @ExampleObject(
                  value = "Addiction successfully deleted"
              )
          })
      }),
      @ApiResponse(responseCode = "400", description = "Quando o vício não pertence ao usuário", content = {
          @Content(mediaType = "text/plain", examples = {
              @ExampleObject(
                  name = "Sem permissão",
                  summary = "Quando o vício não pertence ao usuário autenticado",
                  value = "You are not allowed to delete this addiction"
              )
          })
      }),
      @ApiResponse(responseCode = "404", description = "Quando o vício não é encontrado", content = {
          @Content(mediaType = "text/plain", examples = {
              @ExampleObject(
                  name = "Vício não encontrado",
                  summary = "Quando o ID do vício não corresponde a um registro existente",
                  value = "Vice not found"
              )
          })
      }),
      @ApiResponse(responseCode = "500", description = "Quando ocorre um erro interno durante a exclusão", content = {
          @Content(mediaType = "text/plain", examples = {
              @ExampleObject(
                  name = "Erro interno",
                  summary = "Quando ocorre um erro interno ao processar a requisição",
                  value = "Error when deleting addiction"
              )
          })
      }),
      @ApiResponse(responseCode = "403", description = "Quando o token JWT não for passado, o resultado será um 403 forbidden")
  })
  public ResponseEntity<Object> delete(@PathVariable Long viceId, HttpServletRequest request) {
    try {
      var userId = request.getAttribute("user_id");
      boolean deleted = this.deleteViceUseCase.execute(viceId, UUID.fromString(userId.toString()));

      return ResponseEntity.ok().body("Addiction successfully deleted");
    } catch (NoSuchElementException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Error when deleting addiction");
    }
  }
}
