package br.com.Tramas3030.breakpoint.exceptions;

public class NoteNotFoundException extends RuntimeException {
  public NoteNotFoundException() {
    super("Note not found");
  }
}
