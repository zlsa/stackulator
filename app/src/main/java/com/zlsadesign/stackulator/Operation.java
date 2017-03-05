package com.zlsadesign.stackulator;

public class Operation {

  public static final int NONE = 0;


  public static final int PUSH = 1;
  public static final int DROP = 2;


  public static final int ADD = 10;
  public static final int SUBTRACT = 11;

  public static final int MULTIPLY = 12;
  public static final int DIVIDE = 13;

  // Static method that converts a string into an operation

  public static int toOperation(String op) {

    switch(op) {

      case "push":
        return PUSH;
      case "drop":
        return DROP;


      case "add":
        return ADD;
      case "subtract":
        return SUBTRACT;

      case "multiply":
        return MULTIPLY;
      case "divide":
        return DIVIDE;


      default:
        return NONE;
    }

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

