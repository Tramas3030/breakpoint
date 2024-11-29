package br.com.Tramas3030.breakpoint.modules.vice.controllers;

import br.com.Tramas3030.breakpoint.modules.vice.dto.ViceListResponseDTO;
import br.com.Tramas3030.breakpoint.modules.vice.entities.ViceEntity;
import br.com.Tramas3030.breakpoint.modules.vice.useCase.CreateViceUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/user/vice")
public class ViceController {

  @Autowired
  private CreateViceUseCase createViceUseCase;

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
}
