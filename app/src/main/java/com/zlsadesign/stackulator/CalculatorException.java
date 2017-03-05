package com.zlsadesign.stackulator;

public class CalculatorException extends Exception {

  public static final int NONE = 0;
  public static final int STACK_EMPTY = 1 << 0;
  public static final int BAD_NUMBER = 1 << 1;
  public static final int DIVIDE_BY_ZERO = 1 << 2;

  public int error = NONE;
  public String message;

  public CalculatorException(int error, String message) {
    this.error = error;
    this.message = message;
  }

  public CalculatorException(int error) {
    this(error, "");
  }

}
