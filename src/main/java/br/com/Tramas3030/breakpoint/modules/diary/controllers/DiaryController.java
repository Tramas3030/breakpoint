package br.com.Tramas3030.breakpoint.modules.diary.controllers;

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

import br.com.Tramas3030.breakpoint.modules.diary.dto.DiaryInformationsSummaryDTO;
import br.com.Tramas3030.breakpoint.modules.diary.dto.DiaryNotesListResponseDTO;
import br.com.Tramas3030.breakpoint.modules.diary.dto.UpdateDiaryDTO;
import br.com.Tramas3030.breakpoint.modules.diary.entities.DiaryEntity;
import br.com.Tramas3030.breakpoint.modules.diary.useCase.CreateNoteUseCase;
import br.com.Tramas3030.breakpoint.modules.diary.useCase.DeleteNoteUseCase;
import br.com.Tramas3030.breakpoint.modules.diary.useCase.GetAllNotesUseCase;
import br.com.Tramas3030.breakpoint.modules.diary.useCase.GetNoteInformationsUseCase;
import br.com.Tramas3030.breakpoint.modules.diary.useCase.UpdateNoteInformationsUseCase;
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
@RequestMapping("/user/diary")
@Tag(name = "Diário", description = "Rotas relacionadas as notas do diário")
@SecurityRequirement(name = "jwt_auth")
public class DiaryController {

  @Autowired
  private GetAllNotesUseCase getAllNotesUseCase;

  @Autowired
  private GetNoteInformationsUseCase getNoteInformationsUseCase;

  @Autowired
  private CreateNoteUseCase createNoteUseCase;

  @Autowired
  private DeleteNoteUseCase deleteNoteUseCase;

  @Autowired
  private UpdateNoteInformationsUseCase updateNoteInformationsUseCase;

  @GetMapping("/")
  @Operation(summary = "Listar todas as notas do usuário", description = "Esse método é responsável por listar todas as notas de diário cadastradas pelo usuário na aplicação")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Exemplo de resposta quando o usuário possui notas cadastradas", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = DiaryNotesListResponseDTO.class))
      }),
      @ApiResponse(responseCode = "200", description = "Exemplo de resposta quando o usuário não possui notas cadastradas", content = {
          @Content(mediaType = "text/plain", examples = {
              @ExampleObject(
                  value = "You do not have any registered note."
              )
          })
      }),
      @ApiResponse(responseCode = "403", description = "Quando o token JWT não for passado, o resultado será um 403 forbidden")
  })
  public ResponseEntity<Object> listAllUserNotes(HttpServletRequest request) {
    var userId = request.getAttribute("user_id");

    DiaryNotesListResponseDTO result = this.getAllNotesUseCase.execute(UUID.fromString(userId.toString()));

    if(result.isEmpty()) {
      return ResponseEntity.ok().body("You do not have any registered note.");
    }

    return ResponseEntity.ok().body(result.getAllUserNotes());
  }

  @GetMapping("/{noteId}")
  @Operation(summary = "Obter informações de uma nota específica", description = "Esse método é responsável por obter todas as informações de uma nota específica do usuário")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Exemplo de resposta quando as informações da nota são encontradas com sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = DiaryInformationsSummaryDTO.class))
      }),
      @ApiResponse(responseCode = "400", description = "Erros possíveis: 1) Nota não encontrada ou 2) A nota não pertence ao usuário", content = {
          @Content(mediaType = "text/plain", examples = {
              @ExampleObject(
                  name = "Nota não encontrada",
                  summary = "Quando o ID da nota não corresponde a um registro existente",
                  value = "Note not found"
              ),
              @ExampleObject(
                  name = "Nota não pertence ao usuário",
                  summary = "Quando a nota não pertence ao usuário autenticado",
                  value = "You are not allowed to access this note"
              )
          })
      }),
      @ApiResponse(responseCode = "403", description = "Quando o token JWT não for passado, o resultado será um 403 forbidden")
  })
  public ResponseEntity<Object> getNoteInformations(@PathVariable Long noteId, HttpServletRequest request) {
    var userId = request.getAttribute("user_id");

    try {
      DiaryInformationsSummaryDTO result = this.getNoteInformationsUseCase.execute(noteId, UUID.fromString(userId.toString()));

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{noteId}")
  @Operation(summary = "Atualizar informações de uma nota", description = "Esse método é responsável por atualizar as informações de uma nota específica. Pode atualizar um ou mais campos")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Exemplo de resposta quando as informações da nota são atualizadas com sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = DiaryInformationsSummaryDTO.class))
      }),
      @ApiResponse(responseCode = "400", description = "Erros possíveis: 1) Nota não encontrada ou 2) A nota não pertence ao usuário", content = {
          @Content(mediaType = "text/plain", examples = {
              @ExampleObject(
                  name = "Nota não encontrada",
                  summary = "Quando o ID da nota não corresponde a um registro existente",
                  value = "Note not found"
              ),
              @ExampleObject(
                  name = "Nota não pertence ao usuário",
                  summary = "Quando a nota não pertence ao usuário autenticado",
                  value = "You are not allowed to update this note"
              )
          })
      }),
      @ApiResponse(responseCode = "403", description = "Quando o token JWT não for passado, o resultado será um 403 forbidden")
  })
  public ResponseEntity<Object> update(@PathVariable Long noteId, HttpServletRequest request, @RequestBody UpdateDiaryDTO updateDiaryDTO) {
    var userId = request.getAttribute("user_id");

    try {
      DiaryInformationsSummaryDTO result = this.updateNoteInformationsUseCase.execute(noteId, UUID.fromString(userId.toString()), updateDiaryDTO);

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/")
  @Operation(summary = "Criar uma nova nota", description = "Esse método é responsável por cadastrar uma nova nota de diário para o usuário")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Exemplo de resposta quando a nota é criada com sucesso", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = DiaryNotesListResponseDTO.class))
      }),
      @ApiResponse(responseCode = "400", description = "Erros possíveis: 1) Campos obrigatórios não preenchidos ou 2) Erro interno ao criar a nota", content = {
          @Content(mediaType = "text/plain", examples = {
              @ExampleObject(
                  name = "Título não preenchido",
                  summary = "Quando o campo obrigatório 'title' não é fornecido",
                  value = "O campo [title] não pode ser vazio"
              ),
              @ExampleObject(
                  name = "Texto não preenchido",
                  summary = "Quando o campo obrigatório 'text' não é fornecido",
                  value = "O campo [text] não pode ser vazio"
              ),
              @ExampleObject(
                  name = "Erro interno",
                  summary = "Quando ocorre um erro interno ao processar a requisição",
                  value = "Erro ao criar a nota"
              )
          })
      }),
      @ApiResponse(responseCode = "403", description = "Quando o token JWT não for passado, o resultado será um 403 forbidden")
  })
  public ResponseEntity<Object> create(@Valid @RequestBody DiaryEntity diaryEntity, HttpServletRequest request) {
    try {
      var userId = request.getAttribute("user_id");
      diaryEntity.setUserId(UUID.fromString(userId.toString()));

      DiaryNotesListResponseDTO result = this.createNoteUseCase.execute(diaryEntity);
      return ResponseEntity.ok().body(result.getAllUserNotes());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{noteId}")
  @Operation(summary = "Deletar uma nota", description = "Esse método é responsável por deletar uma nota específica do usuário")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Resposta quando a nota é excluída com sucesso", content = {
          @Content(mediaType = "text/plain", examples = {
              @ExampleObject(
                  value = "Note successfully deleted"
              )
          })
      }),
      @ApiResponse(responseCode = "400", description = "Quando a nota não pertence ao usuário", content = {
          @Content(mediaType = "text/plain", examples = {
              @ExampleObject(
                  name = "Sem permissão",
                  summary = "Quando a nota não pertence ao usuário autenticado",
                  value = "You are not allowed to delete this note"
              )
          })
      }),
      @ApiResponse(responseCode = "404", description = "Quando a nota não é encontrada", content = {
          @Content(mediaType = "text/plain", examples = {
              @ExampleObject(
                  name = "Nota não encontrada",
                  summary = "Quando o ID da nota não corresponde a um registro existente",
                  value = "Note not found"
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
  public ResponseEntity<Object> delete(@PathVariable Long noteId, HttpServletRequest request) {
    try {
      var userId = request.getAttribute("user_id");
      boolean deleted = this.deleteNoteUseCase.execute(noteId, UUID.fromString(userId.toString()));

      return ResponseEntity.ok().body("Note successfully deleted");
    } catch (NoSuchElementException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }  catch (Exception e) {
      return ResponseEntity.internalServerError().body("Error when deleting addiction");
    }
  }

}
