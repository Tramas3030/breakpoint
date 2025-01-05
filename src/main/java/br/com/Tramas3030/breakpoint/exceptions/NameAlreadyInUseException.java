package br.com.Tramas3030.breakpoint.exceptions;

public class NameAlreadyInUseException extends RuntimeException{
  public NameAlreadyInUseException() {
    super("This name is already in use");
  }
}
