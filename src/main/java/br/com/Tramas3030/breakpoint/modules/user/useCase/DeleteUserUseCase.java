package br.com.Tramas3030.breakpoint.modules.user.useCase;

import br.com.Tramas3030.breakpoint.exceptions.UserNotFoundException;
import br.com.Tramas3030.breakpoint.modules.user.entities.UserEntity;
import br.com.Tramas3030.breakpoint.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeleteUserUseCase {

  @Autowired
  private UserRepository userRepository;

  public boolean execute(UUID userId) {
    if(userId == null) {
      throw new UserNotFoundException();
    }

    Optional<UserEntity> userEntity = this.userRepository.findById(userId);
    if(userEntity.isEmpty()) {
      throw new UserNotFoundException();
    }

    this.userRepository.deleteById(userId);

    return true;
  }

}
