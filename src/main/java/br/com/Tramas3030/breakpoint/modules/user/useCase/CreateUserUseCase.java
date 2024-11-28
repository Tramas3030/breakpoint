package br.com.Tramas3030.breakpoint.modules.user.useCase;

import br.com.Tramas3030.breakpoint.exceptions.UserFoundException;
import br.com.Tramas3030.breakpoint.modules.user.entities.UserEntity;
import br.com.Tramas3030.breakpoint.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateUserUseCase {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public UserEntity execute(UserEntity userEntity) {
    this.userRepository.findByEmail(userEntity.getEmail()).ifPresent(user -> {
      throw new UserFoundException();
    });

    var encryptedPassword = this.passwordEncoder.encode(userEntity.getPassword());
    userEntity.setPassword(encryptedPassword);

    System.out.println("Antes de salvar " + userEntity);

    var result = this.userRepository.save(userEntity);

    System.out.println("Depois de salvar " + result);

    return result;
  }

}
