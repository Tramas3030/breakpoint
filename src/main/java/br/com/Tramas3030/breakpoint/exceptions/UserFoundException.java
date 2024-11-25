package br.com.Tramas3030.breakpoint.exceptions;

public class UserFoundException extends RuntimeException {
  public UserFoundException() {
    super("User already exists");
  }
}
