package br.com.Tramas3030.breakpoint.modules.diary.controllers;

import br.com.Tramas3030.breakpoint.modules.diary.dto.DiaryInformationsSummaryDTO;
import br.com.Tramas3030.breakpoint.modules.diary.entities.DiaryEntity;
import br.com.Tramas3030.breakpoint.modules.diary.useCase.CreateNoteUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/user/diary")
public class DiaryController {

  @Autowired
  private CreateNoteUseCase createNoteUseCase;

  @PostMapping("/")
  public ResponseEntity<Object> create(@Valid @RequestBody DiaryEntity diaryEntity, HttpServletRequest request) {
    try {
      var userId = request.getAttribute("user_id");
      diaryEntity.setUserId(UUID.fromString(userId.toString()));

      DiaryInformationsSummaryDTO result = this.createNoteUseCase.execute(diaryEntity);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

}
