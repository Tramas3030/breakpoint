package br.com.Tramas3030.breakpoint.exceptions;

public class ViceNotFoundException extends RuntimeException {
  public ViceNotFoundException() {
    super("Vice not found");
  }
}
