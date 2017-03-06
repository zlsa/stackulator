package com.zlsadesign.stackulator;

import android.util.Log;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import org.apfloat.ApfloatMath;

@JsonObject
public class Operation {

  public static final int NONE = 0;


  public static final int PUSH = 1;
  public static final int POP = 2;


  public static final int SWAP = 5;
  public static final int DUPLICATE = 6;


  public static final int ADD = 10;
  public static final int SUBTRACT = 11;

  public static final int MULTIPLY = 12;
  public static final int DIVIDE = 13;


  public static final int RECIPROCAL = 50;
  public static final int SQUARE = 51;


  public static final int ROOT = 60;
  public static final int POWER = 61;

  public static final int SIN = 100;
  public static final int ASIN = 101;
  public static final int COS = 110;
  public static final int ACOS = 111;
  public static final int TAN = 120;
  public static final int ATAN = 121;

  public static final int INVERT = 300;


  public static final int NATURAL_LOG = 500;
  public static final int LOG = 501;

  public static final int FACTORIAL = 600;
  public static final int MODULO = 601;


  public static final int UNDO = 1000;
  public static final int REDO = 1001;

  // Static method that converts a string into an operation

  public static int toOperationType(String op) {

    switch(op) {

      case "push":
        return PUSH;


      case "swap":
        return SWAP;
      case "pop":
      case "drop":
        return POP;
      case "duplicate":
        return DUPLICATE;


      case "add":
        return ADD;
      case "subtract":
        return SUBTRACT;

      case "multiply":
        return MULTIPLY;
      case "divide":
        return DIVIDE;


      case "square":
        return SQUARE;
      case "reciprocal":
        return RECIPROCAL;


      case "root":
        return ROOT;
      case "power":
        return POWER;


      case "sin":
        return SIN;
      case "cos":
        return COS;
      case "tan":
        return TAN;

      case "asin":
        return ASIN;
      case "acos":
        return ACOS;
      case "atan":
        return ATAN;


      case "invert":
        return INVERT;


      case "natural_log":
        return NATURAL_LOG;
      case "log":
        return LOG;

      case "factorial":
        return FACTORIAL;
      case "modulo":
        return MODULO;


      case "undo":
        return UNDO;
      case "redo":
        return REDO;


      default:
        Log.w("Operation", "unknown operation " + op);
        return NONE;
    }

  }

  public static Operation toOperation(String op) {
    int type = Operation.toOperationType(op);

    StackValue value = null;

    switch(op) {
      case "pi":
        value = new StackValue(ApfloatMath.pi(25));
        break;
      case "euler":
        try {
          value = new StackValue("2.71828182845904523536028747135266249775724709369995");
        } catch(CalculatorException ignored) {

        }
        break;
    }

    if(value != null) {
      Log.d("foo", "pushing value!");
      return new Operation(PUSH, value);
    }

    return new Operation(type);
  }

  @JsonField(name = "operation")
  int operation = NONE;

  @JsonField(name = "value")
  StackValue value;

  public Operation(int operation, StackValue value) {
    this.operation = operation;
    this.value = value;
  }

  public Operation(int operation, String value) throws CalculatorException {
    this(operation, new StackValue(value));
  }

  public Operation(int operation) {
    this.operation = operation;
  }

  public Operation() {
  }

  public int getOperation() {
    return this.operation;
  }

  public StackValue getValue() {
    return this.value;
  }

}

