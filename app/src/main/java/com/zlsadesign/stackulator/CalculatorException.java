package com.zlsadesign.stackulator;

import android.content.Context;
import android.util.Log;

public class CalculatorException extends Exception {

  public static final int UNKNOWN = 0;
  public static final int STACK_EMPTY = 1 << 0;
  public static final int BAD_NUMBER = 1 << 1;
  public static final int DIVIDE_BY_ZERO = 1 << 2;

  public static final int OVERFLOW = 1 << 3;
  public static final int OUT_OF_BOUNDS = 1 << 4;

  public static final int NO_MORE_UNDO = 1 << 5;
  public static final int NO_MORE_REDO = 1 << 6;

  public int error = UNKNOWN;
  public String message;

  public CalculatorException(int error, String message) {
    this.error = error;
    this.message = message;
  }

  public CalculatorException(int error) {
    this(error, "");
  }

  public String toString(Context context) {
    int string = R.string.error_unknown;

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
      case OUT_OF_BOUNDS:
        string = R.string.error_out_of_bounds;
        break;
      case NO_MORE_UNDO:
        string = R.string.error_no_more_undo;
        break;
      case NO_MORE_REDO:
        string = R.string.error_no_more_redo;
        break;
      default:
        Log.w("CalculatorException", "no string for error " + this.error);
    }

    return context.getResources().getString(string);
  }

}
