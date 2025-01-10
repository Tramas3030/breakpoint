package br.com.Tramas3030.breakpoint.exceptions;

public class EqualViceAddictionImpactException extends RuntimeException {
  public EqualViceAddictionImpactException() {
    super("This addiction impact is already in use");
  }
}
