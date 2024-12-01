package br.com.Tramas3030.breakpoint.modules.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiaryNotesListResponseDTO {

  private List<DiaryInformationsSummaryDTO> allUserNotes;

  public boolean isEmpty() {
    return allUserNotes.isEmpty();
  }

}
