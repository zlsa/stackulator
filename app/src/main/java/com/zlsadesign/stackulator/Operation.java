package com.zlsadesign.stackulator;

public class Operation {

  public static final int NONE = 0;


  public static final int PUSH = 1;


  public static final int SWAP = 5;
  public static final int DROP = 6;
  public static final int DUPLICATE = 7;


  public static final int ADD = 10;
  public static final int SUBTRACT = 11;

  public static final int MULTIPLY = 12;
  public static final int DIVIDE = 13;


  public static final int RECIPROCAL = 50;
  public static final int SQUARE = 51;


  public static final int POWER = 60;

  public static final int INVERT = 500;

  // Static method that converts a string into an operation

  public static int toOperationType(String op) {

    switch(op) {

      case "push":
        return PUSH;


      case "swap":
        return SWAP;
      case "drop":
        return DROP;
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


      case "reciprocal":
        return RECIPROCAL;
      case "square":
        return SQUARE;


      case "invert":
        return INVERT;


      default:
        return NONE;
    }

  }

  public static Operation toOperation(String op) {
    return new Operation(Operation.toOperationType(op));
  }

  private int operation;
  private StackValue value;

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

  public int getOperation() {
    return this.operation;
  }

  public StackValue getValue() {
    return this.value;
  }

}

