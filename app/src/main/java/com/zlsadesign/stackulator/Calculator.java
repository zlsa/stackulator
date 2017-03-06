package com.zlsadesign.stackulator;

import android.os.Handler;
import android.util.Log;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.apfloat.ApintMath;
import org.apfloat.LossOfPrecisionException;
import org.apfloat.OverflowException;
import org.apfloat.internal.BackingStorageException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@JsonObject
public class Calculator {

  @JsonField(name = "stack")
  List<StackValue> stack = new ArrayList<>();

  @JsonField(name = "angular_units_degrees")
  boolean use_degrees = true;

  @JsonField(name = "undo_stack")
  List<UndoItem> undo_stack = new ArrayList<>();

  @JsonField(name = "undo_pointer")
  int undo_pointer = -1;

  @JsonField(name = "operation_queue")
  Queue<Operation> operation_queue = new ArrayDeque<>();

  boolean operation_in_progress = false;
  boolean operation_queue_running = false;

  private CalculatorListener listener = null;

  private Handler handler;
  private Runnable run_operation_queue;

  public Calculator() {

    this.saveState();

    this.run_operation_queue = new Runnable() {

      @Override
      public void run() {
        Log.d("Calculator", "queue size: " + operation_queue.size());
        runOperationQueue();
        operation_queue_running = false;

        if(operation_queue.size() > 0) {
          runNextOperation();
        }

      }
    };

  }

  public void setListener(CalculatorListener listener) {
    this.listener = listener;
  }

  // Undo stack

  private void clearUndoAfterPointer() {
    this.undo_stack = this.undo_stack.subList(this.undo_pointer + 1, this.undo_stack.size());
  }

  private void saveState() {

    UndoItem item = new UndoItem();
    item.set(this.stack);

    this.undo_pointer = this.undo_stack.size();

    this.undo_stack.add(item);
  }

  private void undo() throws CalculatorException {
    if(this.undo_pointer <= 0) {
      throw new CalculatorException(CalculatorException.NO_MORE_UNDO);
    }

    this.undo_pointer -= 1;

    UndoItem item = this.undo_stack.get(this.undo_pointer);

    this.stack = new ArrayList<>(item.stack);

  }

  private void redo() throws CalculatorException {
    if(this.undo_pointer >= this.undo_stack.size() - 1) {
      throw new CalculatorException(CalculatorException.NO_MORE_REDO);
    }

    this.undo_pointer += 1;

    UndoItem item = this.undo_stack.get(this.undo_pointer);

    this.stack = new ArrayList<>(item.stack);
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
      this.push(new StackValue(ApfloatMath.pow(y.getValue(), x.getValue())));
    } catch(OverflowException e) {
      this.push(y);
      this.push(x);
      throw new CalculatorException(CalculatorException.OVERFLOW);
    }

  }

  // # `ROOT`

  private void root() throws CalculatorException {
    this.requiresSize(2);

    StackValue x = this.pop();
    StackValue y = this.pop();

    this.push(new StackValue(ApfloatMath.root(y.getValue(), x.getValue().longValue())));

  }

  // # `INVERT`

  private void invert() throws CalculatorException {
    this.requiresSize(1);

    StackValue x = this.pop();

    this.push(new StackValue(x.getValue().negate()));

  }

  private Apfloat toAngle(Apfloat value) {
    if(this.use_degrees) {
      return ApfloatMath.toRadians(value);
    } else {
      return value;
    }
  }

  private Apfloat fromAngle(Apfloat value) {
    if(this.use_degrees) {
      return ApfloatMath.toDegrees(value);
    } else {
      return value;
    }
  }

  // # `SIN`

  private void sin() throws CalculatorException {
    this.requiresSize(1);

    StackValue x = this.pop();

    try {
      this.push(new StackValue(ApfloatMath.sin(this.toAngle(x.getValue()))));
    } catch(LossOfPrecisionException e) {
      this.push(x);
      throw new CalculatorException(CalculatorException.OVERFLOW);
    }

  }

  // # `COS`

  private void cos() throws CalculatorException {
    this.requiresSize(1);

    StackValue x = this.pop();

    try {
      this.push(new StackValue(ApfloatMath.cos(this.toAngle(x.getValue()))));
    } catch(LossOfPrecisionException e) {
      this.push(x);
      throw new CalculatorException(CalculatorException.OVERFLOW);
    }

  }

  // # `TAN`

  private void tan() throws CalculatorException {
    this.requiresSize(1);

    StackValue x = this.pop();

    try {
      this.push(new StackValue(ApfloatMath.tan(this.toAngle(x.getValue()))));
    } catch(LossOfPrecisionException e) {
      this.push(x);
      throw new CalculatorException(CalculatorException.OVERFLOW);
    }

  }

  // # `ASIN`

  private void asin() throws CalculatorException {
    this.requiresSize(1);

    StackValue x = this.pop();

    try {
      this.push(new StackValue(this.fromAngle(ApfloatMath.asin(x.getValue()))));
    } catch(ArithmeticException e) {
      this.push(x);
      throw new CalculatorException(CalculatorException.OUT_OF_BOUNDS);
    }
  }

  // # `ACOS`

  private void acos() throws CalculatorException {
    this.requiresSize(1);

    StackValue x = this.pop();

    try {
      this.push(new StackValue(this.fromAngle(ApfloatMath.acos(x.getValue()))));
    } catch(ArithmeticException e) {
      this.push(x);
      throw new CalculatorException(CalculatorException.OUT_OF_BOUNDS);
    }
  }

  // # `ATAN`

  private void atan() throws CalculatorException {
    this.requiresSize(1);

    StackValue x = this.pop();

    try {
      this.push(new StackValue(this.fromAngle(ApfloatMath.atan(x.getValue()))));
    } catch(ArithmeticException e) {
      this.push(x);
      throw new CalculatorException(CalculatorException.OUT_OF_BOUNDS);
    }
  }

  // # `NATURAL_LOG`

  private void ln() throws CalculatorException {
    this.requiresSize(1);

    StackValue x = this.pop();

    this.push(new StackValue(ApfloatMath.log(x.getValue())));
  }

  // # `LOG`

  private void log() throws CalculatorException {
    this.requiresSize(2);

    StackValue x = this.pop();
    StackValue y = this.pop();

    this.push(new StackValue(ApfloatMath.log(y.getValue(), x.getValue())));
  }

  // # `FACTORIAL`

  private void factorial() throws CalculatorException {
    this.requiresSize(1);

    StackValue x = this.pop();

    try {
      this.push(new StackValue(new Apfloat(String.valueOf(ApintMath.factorial(x.getValue().longValue())))));
    } catch(BackingStorageException e) {
      this.push(x);
      throw new CalculatorException(CalculatorException.OVERFLOW);
    }

  }

  // # `MODULO`

  private void modulo() throws CalculatorException {
    this.requiresSize(2);

    StackValue x = this.pop();
    StackValue y = this.pop();

    this.push(new StackValue(ApfloatMath.fmod(y.getValue(), x.getValue())));
  }

  // # Performs the operation.

  public void runOperationQueue() {

    if(this.operation_queue.isEmpty()) return;

    this.operation_in_progress = true;

    Operation operation = this.operation_queue.remove();

    boolean can_undo = false;

    try {
      can_undo = this.runOperation(operation);

    } catch(CalculatorException e) {
      this.operation_in_progress = false;

      if(this.listener != null)
        this.listener.onCalculatorException(e);
    }

    if(can_undo) this.saveState();

    this.operation_in_progress = false;

    if(this.listener != null) {
      this.listener.onStackChanged();
    }

  }

  private boolean runOperation(Operation operation) throws CalculatorException {

    boolean can_undo = true;

    try {

      switch(operation.getOperation()) {
        case Operation.NONE:
          can_undo = false;
          break;


        case Operation.PUSH:
          this.push(operation.getValue());
          break;


        case Operation.SWAP:
          this.swap();
          break;
        case Operation.POP:
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
          this.invert();
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
        case Operation.ROOT:
          this.root();
          break;


        case Operation.SIN:
          this.sin();
          break;
        case Operation.ASIN:
          this.asin();
          break;

        case Operation.COS:
          this.cos();
          break;
        case Operation.ACOS:
          this.acos();
          break;

        case Operation.TAN:
          this.tan();
          break;
        case Operation.ATAN:
          this.atan();
          break;


        case Operation.NATURAL_LOG:
          this.ln();
          break;
        case Operation.LOG:
          this.log();
          break;


        case Operation.FACTORIAL:
          this.factorial();
          break;
        case Operation.MODULO:
          this.modulo();
          break;


        case Operation.UNDO:
          can_undo = false;
          this.undo();
          break;
        case Operation.REDO:
          can_undo = false;
          this.redo();
          break;


        default:
          Log.w("Calculator", "Operation not supported");
          break;
      }

    } catch(Exception e) {
      if(e instanceof CalculatorException) throw e;
      Log.w("Calculator", e.toString());
      throw new CalculatorException(CalculatorException.UNKNOWN, e.toString());
    }

    return can_undo;

  }

  public void operation(Operation operation) {

    Log.d("Calculator", "adding to queue; queue size: " + operation_queue.size());
    this.operation_queue.add(operation);

    this.runNextOperation();

  }

  public void runNextOperation() {

    if(this.operation_queue_running) {
      Log.d("Calculator", "not starting queue (already running)");
      return;
    }

    this.operation_queue_running = true;

    this.handler = new Handler();
    this.handler.post(this.run_operation_queue);
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

  public void destroy() {

    if(this.handler != null) {
      this.handler.removeCallbacks(this.run_operation_queue);
    }

  }
}
