package br.com.Tramas3030.breakpoint.modules.user.useCase;

import br.com.Tramas3030.breakpoint.exceptions.UserNotFoundException;
import br.com.Tramas3030.breakpoint.modules.user.dto.AuthUserDTO;
import br.com.Tramas3030.breakpoint.modules.user.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class AuthUserUseCase {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Value("${security.token.secret}")
  private String secretKey;

  public String execute(AuthUserDTO authUserDTO) throws BadCredentialsException, UserNotFoundException {
    var user = this.userRepository.findByEmail(authUserDTO.getEmail())
        .orElseThrow(() -> new UserNotFoundException());

    var passwordMatches = this.passwordEncoder.matches(authUserDTO.getPassword(), user.getPassword());

    if (!passwordMatches) {
      throw new BadCredentialsException("Senha inválida");
    }

    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    var token = JWT.create().withIssuer("Breakpoint")
        .withExpiresAt(Instant.now().plus(Duration.ofHours(4)))
        .withSubject(user.getId().toString())
        .sign(algorithm);

    return token;
  }

}
