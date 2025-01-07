package br.com.Tramas3030.breakpoint.exceptions;

public class EqualNoteEmotionException extends RuntimeException {
  public EqualNoteEmotionException() {
    super("This emotion is already in use");
  }
}
