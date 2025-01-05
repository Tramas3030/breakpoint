package br.com.Tramas3030.breakpoint.modules.user.useCase;

import br.com.Tramas3030.breakpoint.exceptions.*;
import br.com.Tramas3030.breakpoint.modules.user.entities.UserEntity;
import br.com.Tramas3030.breakpoint.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateUserInformationsUseCase {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public UserEntity execute(UUID userId, UserEntity updateData) {
    Optional<UserEntity> userEntity = this.userRepository.findById(userId);

    if(userEntity.isEmpty()) {
      throw new UserNotFoundException();
    }

    UserEntity user = userEntity.get();

    if(updateData.getEmail() != null && !updateData.getEmail().isBlank()) {
      if(updateData.getEmail().equals(user.getEmail())) {
        throw new EmailAlreadyInUseException();
      }

      user.setEmail(updateData.getEmail());
    }

    if(updateData.getName() != null && !updateData.getName().isBlank()) {
      if(updateData.getName().equals(user.getName())) {
        throw new NameAlreadyInUseException();
      }

      user.setName(updateData.getName());
    }

    if(updateData.getPassword() != null && !updateData.getPassword().isBlank()) {

      if(updateData.getPassword().length() < 5 || updateData.getPassword().length() > 100) {
        throw new InvalidPasswordException();
      }

      var updatedPasswordEncrypted = this.passwordEncoder.encode(updateData.getPassword());

      if(this.passwordEncoder.matches(updateData.getPassword(), user.getPassword())) {
        throw new PasswordAlreadyInUseException();
      }

      user.setPassword(updatedPasswordEncrypted);
    }

    return userRepository.save(user);
  }
}
