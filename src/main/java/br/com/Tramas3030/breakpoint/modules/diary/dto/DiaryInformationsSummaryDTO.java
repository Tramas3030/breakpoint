package br.com.Tramas3030.breakpoint.modules.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiaryInformationsSummaryDTO {

  private Long id;
  private String title;
  private String text;
  private String emotion;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}
