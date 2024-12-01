package br.com.Tramas3030.breakpoint.modules.diary.controllers;

import br.com.Tramas3030.breakpoint.modules.diary.dto.DiaryInformationsSummaryDTO;
import br.com.Tramas3030.breakpoint.modules.diary.dto.DiaryNotesListResponseDTO;
import br.com.Tramas3030.breakpoint.modules.diary.entities.DiaryEntity;
import br.com.Tramas3030.breakpoint.modules.diary.useCase.CreateNoteUseCase;
import br.com.Tramas3030.breakpoint.modules.diary.useCase.DeleteNoteUseCase;
import br.com.Tramas3030.breakpoint.modules.diary.useCase.GetAllNotesUseCase;
import br.com.Tramas3030.breakpoint.modules.diary.useCase.GetNoteInformationsUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/user/diary")
public class DiaryController {

  @Autowired
  private GetAllNotesUseCase getAllNotesUseCase;

  @Autowired
  private GetNoteInformationsUseCase getNoteInformationsUseCase;

  @Autowired
  private CreateNoteUseCase createNoteUseCase;

  @Autowired
  private DeleteNoteUseCase deleteNoteUseCase;

  @GetMapping("/")
  public ResponseEntity<Object> listAllUserNotes(HttpServletRequest request) {
    var userId = request.getAttribute("user_id");

    DiaryNotesListResponseDTO result = this.getAllNotesUseCase.execute(UUID.fromString(userId.toString()));

    if(result.isEmpty()) {
      return ResponseEntity.ok().body("You do not have any registered note.");
    }

    return ResponseEntity.ok().body(result.getAllUserNotes());
  }

  @GetMapping("/{noteId}")
  public ResponseEntity<Object> getNoteInformations(@PathVariable Long noteId, HttpServletRequest request) {
    var userId = request.getAttribute("user_id");

    try {
      DiaryInformationsSummaryDTO result = this.getNoteInformationsUseCase.execute(noteId, UUID.fromString(userId.toString()));

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/")
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
