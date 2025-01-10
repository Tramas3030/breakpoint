package br.com.Tramas3030.breakpoint.exceptions;

public class EqualViceDescriptionException extends RuntimeException {
  public EqualViceDescriptionException() {
    super("This description already in use");
  }
}
