package br.com.Tramas3030.breakpoint.exceptions;

public class ViceFoundException extends RuntimeException {
  public ViceFoundException() {
    super("This addiction is already registered");
  }
}
