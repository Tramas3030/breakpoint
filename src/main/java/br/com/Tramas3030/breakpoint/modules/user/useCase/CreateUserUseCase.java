package br.com.Tramas3030.breakpoint.modules.user.useCase;

import br.com.Tramas3030.breakpoint.exceptions.InvalidPasswordException;
import br.com.Tramas3030.breakpoint.exceptions.UserFoundException;
import br.com.Tramas3030.breakpoint.modules.user.entities.UserEntity;
import br.com.Tramas3030.breakpoint.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public UserEntity execute(UserEntity userEntity) {
    this.userRepository.findByEmail(userEntity.getEmail()).ifPresent(user -> {
      throw new UserFoundException();
    });

    if(userEntity.getPassword().length() < 5 || userEntity.getPassword().length() > 100) {
      throw new InvalidPasswordException();
    }

    var encryptedPassword = this.passwordEncoder.encode(userEntity.getPassword());
    userEntity.setPassword(encryptedPassword);

    return this.userRepository.save(userEntity);
  }

}
