package br.com.Tramas3030.breakpoint.modules.user.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "users")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank(message = "O campo [nome] não pode ficar em branco")
  private String name;

  @Email(message = "O campo [email] deve conter um email válido")
  @NotBlank(message = "O campo [email] não pode ficar em branco")
  private String email;

  private String password;

  @Column(name = "created_at")
  @CreationTimestamp
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  private LocalDateTime updatedAt;

}
