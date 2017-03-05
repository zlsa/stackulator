package com.zlsadesign.stackulator;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

  private List<StackValue> stack = new ArrayList<>();

  public Calculator() {

  }

  // Push/Pop operations

  private StackValue pop() throws CalculatorException {
    this.requiresSize(1);

    int index = this.stack.size() - 1;

    StackValue value = this.stack.get(index);

    this.stack.remove(index);

    return value;
  }

  private void push(StackValue value) {
    this.stack.add(value);
  }

  private void push(String value) throws CalculatorException {
    this.push(new StackValue(value));
  }

  // Actual operations start here.
  // # `ADD`

  private void add() throws CalculatorException {
    this.requiresSize(2);

    StackValue x = this.pop();
    StackValue y = this.pop();

    this.push(new StackValue(x.getValue().add(y.getValue())));
  }

  // # `SUBTRACT`

  private void subtract() throws CalculatorException {
    this.requiresSize(2);

    StackValue x = this.pop();
    StackValue y = this.pop();

    this.push(new StackValue(y.getValue().subtract(x.getValue())));
  }

  // # `MULTIPLY`

  private void multiply() throws CalculatorException {
    this.requiresSize(2);

    StackValue x = this.pop();
    StackValue y = this.pop();

    this.push(new StackValue(x.getValue().multiply(y.getValue())));
  }

  // # `DIVIDE`

  private void divide() throws CalculatorException {
    this.requiresSize(2);

    StackValue x = this.pop();
    StackValue y = this.pop();

    this.push(new StackValue(y.getValue().divide(x.getValue())));
  }

  // # Performs the operation.

  public void operation(Operation operation) throws CalculatorException {

    switch(operation.getOperation()) {
      case Operation.NONE:
        break;


      case Operation.ADD:
        this.add();
        break;
      case Operation.SUBTRACT:
        this.subtract();
        break;

      case Operation.MULTIPLY:
        this.multiply();
        break;
      case Operation.DIVIDE:
        this.divide();
        break;


      case Operation.PUSH:
        this.push(operation.getValue());
        break;
      case Operation.DROP:
        this.pop();
        break;

      default:
        throw new RuntimeException("Operation not supported");
    }

  }

  // Performs a simple operation.
  public void operation(int operation) throws CalculatorException {
    this.operation(new Operation(operation));
  }

  // Pushes the number onto the stack.
  public void operation(String value) throws CalculatorException {
    this.operation(new Operation(Operation.PUSH, new StackValue(value)));
  }

  // Throws an error if the stack contains less than `size` elements.

  public void requiresSize(int size) throws CalculatorException {
    if(this.size() < size) {
      throw new CalculatorException(CalculatorException.STACK_EMPTY);
    }
  }

  public StackValue get(int position) throws CalculatorException {
    this.requiresSize(position + 1);

    return this.stack.get(position);
  }

  public int size() {
    return this.stack.size();
  }

}
