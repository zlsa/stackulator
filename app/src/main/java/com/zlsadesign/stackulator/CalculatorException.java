package com.zlsadesign.stackulator;

import android.content.Context;

public class CalculatorException extends Exception {

  public static final int NONE = 0;
  public static final int STACK_EMPTY = 1 << 0;
  public static final int BAD_NUMBER = 1 << 1;
  public static final int DIVIDE_BY_ZERO = 1 << 2;

  public static final int OVERFLOW = 1 << 3;

  public int error = NONE;
  public String message;

  public CalculatorException(int error, String message) {
    this.error = error;
    this.message = message;
  }

  public CalculatorException(int error) {
    this(error, "");
  }

  public String toString(Context context) {
    int string = R.string.error_none;

    switch(this.error) {
      case STACK_EMPTY:
        string = R.string.error_stack_empty;
        break;
      case BAD_NUMBER:
        string = R.string.error_bad_number;
        break;
      case DIVIDE_BY_ZERO:
        string = R.string.error_divide_by_zero;
        break;
      case OVERFLOW:
        string = R.string.error_overflow;
        break;
    }

    return context.getResources().getString(string);
  }

}
