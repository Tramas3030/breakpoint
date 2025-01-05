package br.com.Tramas3030.breakpoint.exceptions;

public class EmailAlreadyInUseException extends RuntimeException {
  public EmailAlreadyInUseException() {
    super("This email is already in use");
  }
}
