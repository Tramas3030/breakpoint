package br.com.Tramas3030.breakpoint.exceptions;

public class EqualViceCriticalHoursException extends RuntimeException {
  public EqualViceCriticalHoursException() {
    super("These hours are already in use");
  }
}
