package br.com.Tramas3030.breakpoint.modules.diary.useCase;

import br.com.Tramas3030.breakpoint.modules.diary.dto.DiaryInformationsSummaryDTO;
import br.com.Tramas3030.breakpoint.modules.diary.entities.DiaryEntity;
import br.com.Tramas3030.breakpoint.modules.diary.repository.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateNoteUseCase {

  @Autowired
  private DiaryRepository diaryRepository;

  public DiaryInformationsSummaryDTO execute(DiaryEntity diaryEntity) {
    this.diaryRepository.save(diaryEntity);

    return DiaryInformationsSummaryDTO.builder()
        .id(diaryEntity.getId())
        .title(diaryEntity.getTitle())
        .text(diaryEntity.getText())
        .emotion(diaryEntity.getEmotion())
        .createdAt(diaryEntity.getCreatedAt())
        .updatedAt(diaryEntity.getUpdated_at())
        .build();
  }

}
