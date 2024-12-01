package br.com.Tramas3030.breakpoint.modules.diary.entities;

import br.com.Tramas3030.breakpoint.modules.user.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "diary")
public class DiaryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotBlank(message = "O campo [title] não pode ser vazio")
  private String title;

  @NotBlank(message = "O campo [text] não pode ser vazio")
  private String text;

  private String emotion;

  @ManyToOne()
  @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
  private UserEntity userEntity;

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "created_at")
  @CreationTimestamp
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  private LocalDateTime updated_at;

}
