package br.com.Tramas3030.breakpoint.exceptions;

public class EqualNoteTitleException extends RuntimeException {
  public EqualNoteTitleException() {
    super("This title is already in use");
  }
}
