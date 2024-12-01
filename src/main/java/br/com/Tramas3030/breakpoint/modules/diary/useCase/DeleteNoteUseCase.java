package br.com.Tramas3030.breakpoint.modules.diary.useCase;

import br.com.Tramas3030.breakpoint.exceptions.UserNotFoundException;
import br.com.Tramas3030.breakpoint.modules.diary.entities.DiaryEntity;
import br.com.Tramas3030.breakpoint.modules.diary.repository.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class DeleteNoteUseCase {

  @Autowired
  private DiaryRepository diaryRepository;

  public boolean execute(Long noteId, UUID userId) {
    if(userId == null) {
      throw new UserNotFoundException();
    }

    DiaryEntity note = diaryRepository.findById(noteId).orElseThrow(() -> new NoSuchElementException("Note not found"));

    if(!note.getUserId().equals(userId)) {
      throw new IllegalArgumentException("You are not allowed to delete this note");
    }

    diaryRepository.delete(note);
    return true;
  }

}
