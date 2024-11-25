package br.com.Tramas3030.breakpoint.modules.vice.useCase;

import br.com.Tramas3030.breakpoint.exceptions.ViceFoundException;
import br.com.Tramas3030.breakpoint.modules.vice.entities.ViceEntity;
import br.com.Tramas3030.breakpoint.modules.vice.repository.ViceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateViceUseCase {

  @Autowired
  private ViceRepository viceRepository;

  public ViceEntity execute(ViceEntity viceEntity) {
    this.viceRepository.findByTitle(viceEntity.getTitle()).ifPresent(vice -> {
      throw new ViceFoundException();
    });

    return this.viceRepository.save(viceEntity);
  }
}
