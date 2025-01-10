package br.com.Tramas3030.breakpoint.exceptions;

public class EqualViceImpactCostException extends RuntimeException {
  public EqualViceImpactCostException() {
    super("This impact cost is already in use");
  }
}
