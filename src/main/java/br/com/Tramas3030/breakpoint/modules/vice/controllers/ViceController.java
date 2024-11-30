package br.com.Tramas3030.breakpoint.modules.vice.controllers;

import br.com.Tramas3030.breakpoint.modules.vice.dto.ViceListResponseDTO;
import br.com.Tramas3030.breakpoint.modules.vice.entities.ViceEntity;
import br.com.Tramas3030.breakpoint.modules.vice.useCase.CreateViceUseCase;
import br.com.Tramas3030.breakpoint.modules.vice.useCase.DeleteViceUseCase;
import br.com.Tramas3030.breakpoint.modules.vice.useCase.GetAllVicesUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/user/vice")
public class ViceController {

  @Autowired
  private GetAllVicesUseCase getAllVicesUseCase;

  @Autowired
  private CreateViceUseCase createViceUseCase;

  @Autowired
  private DeleteViceUseCase deleteViceUseCase;

  @GetMapping("/")
  public ResponseEntity<Object> listAllUserVices(HttpServletRequest request) {
    var userId = request.getAttribute("user_id");

    ViceListResponseDTO result = getAllVicesUseCase.execute(UUID.fromString(userId.toString()));

    if(result.isEmpty()) {
      return ResponseEntity.ok().body("You do not have any registered addiction.");
    }

    return ResponseEntity.ok().body(result.getAllUserVices());
  }

  @PostMapping("/")
  public ResponseEntity<Object> create(@Valid @RequestBody ViceEntity viceEntity, HttpServletRequest request) {
    try {
      var userId = request.getAttribute("user_id");
      viceEntity.setUserId(UUID.fromString(userId.toString()));

      ViceListResponseDTO result = this.createViceUseCase.execute(viceEntity);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{viceId}")
  public ResponseEntity<Object> delete(@PathVariable Long viceId, HttpServletRequest request) {
    try {
      var userId = request.getAttribute("user_id");
      boolean deleted = this.deleteViceUseCase.execute(viceId, UUID.fromString(userId.toString()));

      return ResponseEntity.ok().body("Addiction successfully deleted");
    } catch (NoSuchElementException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Error when deleting addiction");
    }
  }
}
