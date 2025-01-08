package br.com.Tramas3030.breakpoint.modules.user.entities;

import br.com.Tramas3030.breakpoint.modules.diary.entities.DiaryEntity;
import br.com.Tramas3030.breakpoint.modules.vice.entities.ViceEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "users")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Schema(hidden = true)
  private UUID id;

  @NotBlank(message = "O campo [nome] não pode ficar em branco")
  @Schema(example = "Simone Simons", requiredMode = Schema.RequiredMode.REQUIRED)
  private String name;

  @Email(message = "O campo [email] deve conter um email válido")
  @NotBlank(message = "O campo [email] não pode ficar em branco")
  @Schema(example = "simonesimons@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
  private String email;

  @Length(min = 5, max = 100, message = "A senha deve conter entre (5) e (100) caracteres")
  @Schema(example = "Strong_password123", minLength = 5, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED)
  private String password;

  @Column(name = "created_at")
  @CreationTimestamp
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  @Schema(hidden = true)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  @Schema(hidden = true)
  private LocalDateTime updatedAt;

  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE, orphanRemoval = true)
  @Schema(hidden = true)
  private List<ViceEntity> vices;

  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE, orphanRemoval = true)
  @Schema(hidden = true)
  private List<DiaryEntity> notes;

}
