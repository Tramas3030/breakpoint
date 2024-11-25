package br.com.Tramas3030.breakpoint.modules.user.useCase;

import br.com.Tramas3030.breakpoint.exceptions.UserFoundException;
import br.com.Tramas3030.breakpoint.modules.user.entities.UserEntity;
import br.com.Tramas3030.breakpoint.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {

  @Autowired
  private UserRepository userRepository;

  public UserEntity execute(UserEntity userEntity) {
    this.userRepository.findByEmail(userEntity.getEmail()).ifPresent(user -> {
      throw new UserFoundException();
    });

    return this.userRepository.save(userEntity);
  }

}
