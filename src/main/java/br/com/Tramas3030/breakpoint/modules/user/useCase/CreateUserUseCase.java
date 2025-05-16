package br.com.Tramas3030.breakpoint.modules.user.useCase;

import br.com.Tramas3030.breakpoint.exceptions.InvalidPasswordException;
import br.com.Tramas3030.breakpoint.exceptions.UserFoundException;
import br.com.Tramas3030.breakpoint.modules.user.dto.CreateUserDTO;
import br.com.Tramas3030.breakpoint.modules.user.dto.ResponseCreateUserDTO;
import br.com.Tramas3030.breakpoint.modules.user.mapper.UserMapper;
import br.com.Tramas3030.breakpoint.modules.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserMapper userMapper;

  public ResponseCreateUserDTO execute(CreateUserDTO createUserDTO) {
    userRepository.findByEmail(createUserDTO.email()).ifPresent(user -> {
      throw new UserFoundException();
    });

    if(createUserDTO.password().length() < 5 || createUserDTO.password().length() > 100) {
      throw new InvalidPasswordException();
    }

    var userEntity = userMapper.toEntity(createUserDTO);

    var savedUser = userRepository.save(userEntity);

    return userMapper.toResponseDTO(savedUser);
  }

}
