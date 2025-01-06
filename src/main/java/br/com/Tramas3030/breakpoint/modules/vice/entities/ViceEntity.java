package br.com.Tramas3030.breakpoint.modules.vice.entities;

import br.com.Tramas3030.breakpoint.modules.user.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "vice")
public class ViceEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotBlank(message = "O campo [title] não pode ser vazio")
  private String title;

  @NotBlank
  private String description;

  @Column(name = "addiction_impact")
  @NotBlank
  private String addictionImpact;

  @Column(name = "impact_cost")
  private String impactCost;

  @ElementCollection
  @Column(name = "critical_hours")
  private List<String> criticalHours;

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
