package br.com.Tramas3030.breakpoint.modules.user.useCase;

import br.com.Tramas3030.breakpoint.exceptions.UserNotFoundException;
import br.com.Tramas3030.breakpoint.modules.user.dto.UserInformationsDTO;
import br.com.Tramas3030.breakpoint.modules.user.entities.UserEntity;
import br.com.Tramas3030.breakpoint.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetUserInformationsUseCase {

  @Autowired
  private UserRepository userRepository;

  public UserInformationsDTO execute(UUID userId) {
    Optional<UserEntity> user = this.userRepository.findById(userId);

    if(user.isEmpty()) {
      throw new UserNotFoundException();
    }

    UserEntity entity = user.get();

    return UserInformationsDTO.builder()
        .name(entity.getName())
        .email(entity.getEmail())
        .createdAt(entity.getCreatedAt())
        .build();
  }

}
