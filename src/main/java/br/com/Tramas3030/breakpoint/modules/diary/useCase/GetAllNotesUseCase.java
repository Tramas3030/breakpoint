package br.com.Tramas3030.breakpoint.modules.diary.useCase;

import br.com.Tramas3030.breakpoint.modules.diary.dto.DiaryInformationsSummaryDTO;
import br.com.Tramas3030.breakpoint.modules.diary.dto.DiaryNotesListResponseDTO;
import br.com.Tramas3030.breakpoint.modules.diary.entities.DiaryEntity;
import br.com.Tramas3030.breakpoint.modules.diary.repository.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetAllNotesUseCase {

  @Autowired
  private DiaryRepository diaryRepository;

  public DiaryNotesListResponseDTO execute(UUID userId) {
    List<DiaryEntity> allUserNotes = this.diaryRepository.findAllByUserId(userId);

    List<DiaryInformationsSummaryDTO> noteSummaryList = allUserNotes.stream()
        .map(note -> DiaryInformationsSummaryDTO.builder()
            .id(note.getId())
            .title(note.getTitle())
            .text(note.getText())
            .emotion(note.getEmotion())
            .createdAt(note.getCreatedAt())
            .updatedAt(note.getUpdated_at())
            .build())
        .toList();

    return DiaryNotesListResponseDTO.builder()
        .allUserNotes(noteSummaryList)
        .build();
  }

}
