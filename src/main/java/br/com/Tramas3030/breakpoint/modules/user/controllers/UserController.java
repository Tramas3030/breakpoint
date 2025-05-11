package br.com.Tramas3030.breakpoint.modules.user.controllers;

import br.com.Tramas3030.breakpoint.docs.UserControllerDocs;
import br.com.Tramas3030.breakpoint.modules.user.entities.UserEntity;
import br.com.Tramas3030.breakpoint.modules.user.useCase.CreateUserUseCase;
import br.com.Tramas3030.breakpoint.modules.user.useCase.DeleteUserUseCase;
import br.com.Tramas3030.breakpoint.modules.user.useCase.GetUserInformationsUseCase;
import br.com.Tramas3030.breakpoint.modules.user.useCase.UpdateUserInformationsUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController implements UserControllerDocs {

  @Autowired
  private CreateUserUseCase createUserUseCase;

  @Autowired
  private GetUserInformationsUseCase getUserInformationsUseCase;

  @Autowired
  private UpdateUserInformationsUseCase updateUserInformationsUseCase;

  @Autowired
  private DeleteUserUseCase deleteUserUseCase;

  @PostMapping("/")
  public ResponseEntity<Object> create(@Valid @RequestBody UserEntity userEntity) {
    try {
      var result = createUserUseCase.execute(userEntity);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/")
  public ResponseEntity<Object> getUserInformations(HttpServletRequest request) {
    var userId = request.getAttribute("user_id");

    try {
      var result = getUserInformationsUseCase.execute(UUID.fromString(userId.toString()));
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/")
  public ResponseEntity<Object> update(HttpServletRequest request, @RequestBody UserEntity userEntity) {
    var userId = request.getAttribute("user_id");

    try {
      var result = updateUserInformationsUseCase.execute(UUID.fromString(userId.toString()), userEntity);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/")
  public ResponseEntity<Object> delete(HttpServletRequest request) {
    var userId = request.getAttribute("user_id");

    try {
      boolean deleted = this.deleteUserUseCase.execute(UUID.fromString(userId.toString()));

      return ResponseEntity.ok().body("User successfully deleted");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}