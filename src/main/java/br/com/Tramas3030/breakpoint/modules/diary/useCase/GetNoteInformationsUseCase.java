package br.com.Tramas3030.breakpoint.modules.diary.useCase;

import br.com.Tramas3030.breakpoint.exceptions.NoteNotFoundException;
import br.com.Tramas3030.breakpoint.modules.diary.dto.DiaryInformationsSummaryDTO;
import br.com.Tramas3030.breakpoint.modules.diary.entities.DiaryEntity;
import br.com.Tramas3030.breakpoint.modules.diary.repository.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetNoteInformationsUseCase {

  @Autowired
  private DiaryRepository diaryRepository;

  public DiaryInformationsSummaryDTO execute(Long noteId, UUID userId) {
    Optional<DiaryEntity> note = this.diaryRepository.findByIdAndUserId(noteId, userId);

    if(note.isEmpty()) {
      throw new NoteNotFoundException();
    }

    DiaryEntity entity = note.get();

    return DiaryInformationsSummaryDTO.builder()
        .id(entity.getId())
        .title(entity.getTitle())
        .text(entity.getText())
        .emotion(entity.getEmotion())
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdated_at())
        .build();
  }

}
