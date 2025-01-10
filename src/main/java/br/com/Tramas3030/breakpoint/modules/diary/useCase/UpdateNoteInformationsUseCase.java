package br.com.Tramas3030.breakpoint.modules.diary.useCase;

import br.com.Tramas3030.breakpoint.exceptions.EqualNoteEmotionException;
import br.com.Tramas3030.breakpoint.exceptions.EqualNoteTextException;
import br.com.Tramas3030.breakpoint.exceptions.EqualNoteTitleException;
import br.com.Tramas3030.breakpoint.exceptions.NoteNotFoundException;
import br.com.Tramas3030.breakpoint.modules.diary.dto.DiaryInformationsSummaryDTO;
import br.com.Tramas3030.breakpoint.modules.diary.dto.UpdateDiaryDTO;
import br.com.Tramas3030.breakpoint.modules.diary.entities.DiaryEntity;
import br.com.Tramas3030.breakpoint.modules.diary.repository.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateNoteInformationsUseCase {

  @Autowired
  private DiaryRepository diaryRepository;

  public DiaryInformationsSummaryDTO execute(Long noteId, UUID userId, UpdateDiaryDTO updateDiaryDTO) {
    Optional<DiaryEntity> noteDiaryEntity = this.diaryRepository.findByIdAndUserId(noteId, userId);

    if(noteDiaryEntity.isEmpty()) {
      throw new NoteNotFoundException();
    }

    DiaryEntity noteDiary = noteDiaryEntity.get();

    if(updateDiaryDTO.getTitle() != null && !updateDiaryDTO.getTitle().isBlank()) {
      if(updateDiaryDTO.getTitle().equals(noteDiary.getTitle())) {
        throw new EqualNoteTitleException();
      }

      noteDiary.setTitle(updateDiaryDTO.getTitle());
    }

    if(updateDiaryDTO.getText() != null && !updateDiaryDTO.getText().isBlank()) {
      if(updateDiaryDTO.getText().equals(noteDiary.getText())) {
        throw new EqualNoteTextException();
      }

      noteDiary.setText(updateDiaryDTO.getText());
    }

    if(updateDiaryDTO.getEmotion() != null && !updateDiaryDTO.getEmotion().isBlank()) {
      if(updateDiaryDTO.getEmotion().equals(noteDiary.getEmotion())) {
        throw new EqualNoteEmotionException();
      }

      noteDiary.setEmotion(updateDiaryDTO.getEmotion());
    }

    diaryRepository.save(noteDiary);

    return DiaryInformationsSummaryDTO.builder()
        .id(noteDiary.getId())
        .title(noteDiary.getTitle())
        .text(noteDiary.getText())
        .emotion(noteDiary.getEmotion())
        .createdAt(noteDiary.getCreatedAt())
        .updatedAt(noteDiary.getUpdated_at())
        .build();
  }

}
