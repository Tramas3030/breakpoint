package br.com.Tramas3030.breakpoint.exceptions;

public class PasswordAlreadyInUseException extends RuntimeException {
  public PasswordAlreadyInUseException() {
    super("This password is already in use");
  }
}
