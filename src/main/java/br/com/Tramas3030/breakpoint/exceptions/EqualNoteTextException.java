package br.com.Tramas3030.breakpoint.exceptions;

public class EqualNoteTextException extends RuntimeException {
  public EqualNoteTextException() {
    super("This text is already in use");
  }
}
