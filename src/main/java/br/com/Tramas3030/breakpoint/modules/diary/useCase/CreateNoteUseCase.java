package br.com.Tramas3030.breakpoint.modules.diary.useCase;

import br.com.Tramas3030.breakpoint.modules.diary.dto.DiaryInformationsSummaryDTO;
import br.com.Tramas3030.breakpoint.modules.diary.dto.DiaryNotesListResponseDTO;
import br.com.Tramas3030.breakpoint.modules.diary.entities.DiaryEntity;
import br.com.Tramas3030.breakpoint.modules.diary.repository.DiaryRepository;
import br.com.Tramas3030.breakpoint.modules.vice.dto.ViceSummaryResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateNoteUseCase {

  @Autowired
  private DiaryRepository diaryRepository;

  public DiaryNotesListResponseDTO execute(DiaryEntity diaryEntity) {
    this.diaryRepository.save(diaryEntity);

    List<DiaryEntity> allUserNotes = this.diaryRepository.findAllByUserId(diaryEntity.getUserId());

    List<DiaryInformationsSummaryDTO> diaryInformationsSummaryDTOList = allUserNotes.stream()
        .map(note -> DiaryInformationsSummaryDTO.builder()
            .id(note.getId())
            .title(note.getTitle())
            .text(note.getText())
            .emotion(note.getEmotion())
            .createdAt(note.getCreatedAt())
            .updatedAt(note.getUpdated_at())
            .build()).toList();

    return DiaryNotesListResponseDTO.builder()
        .allUserNotes(diaryInformationsSummaryDTOList)
        .build();
  }

}
