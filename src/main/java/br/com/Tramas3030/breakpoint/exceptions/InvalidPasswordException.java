package br.com.Tramas3030.breakpoint.exceptions;

public class InvalidPasswordException extends RuntimeException {
  public InvalidPasswordException() {
    super("Password must be between 5 and 100 characters");
  }
}
