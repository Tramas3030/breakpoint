package br.com.Tramas3030.breakpoint.modules.vice.controllers;

import br.com.Tramas3030.breakpoint.modules.vice.dto.*;
import br.com.Tramas3030.breakpoint.modules.vice.entities.ViceEntity;
import br.com.Tramas3030.breakpoint.modules.vice.useCase.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/user/vice")
@Tag(name = "Vícios", description = "Rotas relacionadas aos vícios")
@SecurityRequirement(name = "jwt_auth")
public class ViceController {

  @Autowired
  private GetAllVicesUseCase getAllVicesUseCase;

  @Autowired
  private GetViceInformationsUseCase getViceInformationsUseCase;

  @Autowired
  private CreateViceUseCase createViceUseCase;

  @Autowired
  private DeleteViceUseCase deleteViceUseCase;

  @Autowired
  private ResetViceTimerUseCase resetViceTimerUseCase;

  @Autowired
  private UpdateViceInformationsUseCase updateViceInformationsUseCase;

  @GetMapping("/")
  public ResponseEntity<Object> listAllUserVices(HttpServletRequest request) {
    var userId = request.getAttribute("user_id");

    ViceSummaryListResponseDTO result = getAllVicesUseCase.execute(UUID.fromString(userId.toString()));

    if(result.isEmpty()) {
      return ResponseEntity.ok().body("You do not have any registered addiction.");
    }

    return ResponseEntity.ok().body(result.getAllUserVices());
  }

  @GetMapping("/{viceId}")
  public ResponseEntity<Object> getViceInformations(@PathVariable Long viceId, HttpServletRequest request) {
    var userId = request.getAttribute("user_id");

    try {
      ViceResponseDTO result = this.getViceInformationsUseCase.execute(viceId, UUID.fromString(userId.toString()));

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{viceId}/reset")
  public ResponseEntity<Object> resetViceTimer(@PathVariable Long viceId, HttpServletRequest request) {
    var userId = request.getAttribute("user_id");

    try {
      ViceUpdatedAt result = this.resetViceTimerUseCase.execute(viceId, UUID.fromString(userId.toString()));

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{viceId}/update")
  public ResponseEntity<Object> changeViceInformations(@PathVariable Long viceId, HttpServletRequest request, @RequestBody UpdateViceDTO updateViceDTO) {
    var userId = request.getAttribute("user_id");

    try {
      ViceResponseDTO result = this.updateViceInformationsUseCase.execute(viceId, UUID.fromString(userId.toString()), updateViceDTO);

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/")
  public ResponseEntity<Object> create(@Valid @RequestBody ViceEntity viceEntity, HttpServletRequest request) {
    try {
      var userId = request.getAttribute("user_id");
      viceEntity.setUserId(UUID.fromString(userId.toString()));

      ViceSummaryListResponseDTO result = this.createViceUseCase.execute(viceEntity);
      return ResponseEntity.ok().body(result.getAllUserVices());
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
