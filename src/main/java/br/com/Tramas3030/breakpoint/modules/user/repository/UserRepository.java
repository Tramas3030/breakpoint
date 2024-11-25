package br.com.Tramas3030.breakpoint.modules.user.repository;

import br.com.Tramas3030.breakpoint.modules.user.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
  Optional<UserEntity> findByEmail(String email);
}
