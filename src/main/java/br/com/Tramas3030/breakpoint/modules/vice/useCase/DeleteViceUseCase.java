package br.com.Tramas3030.breakpoint.modules.vice.useCase;

import br.com.Tramas3030.breakpoint.exceptions.UserNotFoundException;
import br.com.Tramas3030.breakpoint.modules.vice.entities.ViceEntity;
import br.com.Tramas3030.breakpoint.modules.vice.repository.ViceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeleteViceUseCase {

  @Autowired
  private ViceRepository viceRepository;

  public boolean execute(Long viceId, UUID userId) {
    if(userId == null) {
      throw new UserNotFoundException();
    }

    ViceEntity vice = viceRepository.findById(viceId).orElseThrow(() -> new NoSuchElementException("Vice not found"));

    if(!vice.getUserId().equals(userId)) {
      throw new IllegalArgumentException("You are not allowed to delete this addiction");
    }

    viceRepository.delete(vice);
    return true;
  }
}
