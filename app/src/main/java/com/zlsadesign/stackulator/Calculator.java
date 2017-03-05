package com.zlsadesign.stackulator;

import android.util.Log;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.apfloat.OverflowException;

import java.util.ArrayList;
import java.util.List;

public class Calculator {

  private List<StackValue> stack = new ArrayList<>();

  public Calculator() {

  }

  // Push/Pop operations

  public StackValue pop() throws CalculatorException {
    this.requiresSize(1);

    int index = this.stack.size() - 1;

    StackValue value = this.stack.get(index);

    this.stack.remove(index);

    return value;
  }

  public StackValue peek() throws CalculatorException {
    this.requiresSize(1);

    int index = this.stack.size() - 1;

    return this.stack.get(index);
  }

  public void push(StackValue value) {
    this.stack.add(value);
  }

  private void push(String value) throws CalculatorException {
    this.push(new StackValue(value));
  }

  // Actual operations start here.
  // # `SWAP`

  private void swap() throws CalculatorException {
    this.requiresSize(2);

    StackValue x = this.pop();
    StackValue y = this.pop();

    this.push(x);
    this.push(y);
  }

  // # `ADD`

  private void add() throws CalculatorException {
    this.requiresSize(2);

    StackValue x = this.pop();
    StackValue y = this.pop();

    try {
      this.push(new StackValue(x.getValue().add(y.getValue())));
    } catch(OverflowException e) {
      this.push(y);
      this.push(x);
      throw new CalculatorException(CalculatorException.OVERFLOW);
    }
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

    try {
      this.push(new StackValue(x.getValue().multiply(y.getValue())));
    } catch(OverflowException e) {
      this.push(y);
      this.push(x);
      throw new CalculatorException(CalculatorException.OVERFLOW);
    }

  }

  // # `DIVIDE`

  private void divide() throws CalculatorException {
    this.requiresSize(2);

    StackValue x = this.pop();
    StackValue y = this.pop();

    try {
      this.push(new StackValue(y.getValue().divide(x.getValue())));
    } catch(OverflowException e) {
      this.push(y);
      this.push(x);
      throw new CalculatorException(CalculatorException.OVERFLOW);
    } catch(ArithmeticException e) {
      this.push(y);
      this.push(x);
      throw new CalculatorException(CalculatorException.DIVIDE_BY_ZERO);

    }
  }

  // # `POWER`

  private void power() throws CalculatorException {
    this.requiresSize(2);

    StackValue x = this.pop();
    StackValue y = this.pop();

    try {
      this.push(new StackValue(ApfloatMath.pow(x.getValue(), y.getValue())));
    } catch(OverflowException e) {
      this.push(y);
      this.push(x);
      throw new CalculatorException(CalculatorException.OVERFLOW);
    }

  }

  // # Performs the operation.

  public void operation(Operation operation) throws CalculatorException {

    switch(operation.getOperation()) {
      case Operation.NONE:
        break;


      case Operation.PUSH:
        this.push(operation.getValue());
        break;


      case Operation.SWAP:
        this.swap();
        break;
      case Operation.DROP:
        this.pop();
        break;
      case Operation.DUPLICATE:
        this.push(this.peek());
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


      case Operation.INVERT:
        this.push("-1");
        this.operation(Operation.MULTIPLY);
        break;


      case Operation.RECIPROCAL:
        this.push("1");
        this.operation(Operation.SWAP);
        this.operation(Operation.DIVIDE);
        break;
      case Operation.SQUARE:
        this.push("2");
        try {
          this.operation(Operation.POWER);
        } catch(CalculatorException e) {
          // Clean up our mess
          this.pop();
          throw e;
        }
        break;


      case Operation.POWER:
        this.power();
        break;


      default:
        Log.w("Calculator", "Operation not supported");
        break;
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

  public AbstractStackValue get(int position) throws CalculatorException {
    this.requiresSize(position + 1);

    return this.stack.get(position);
  }

  public int size() {
    return this.stack.size();
  }

}
