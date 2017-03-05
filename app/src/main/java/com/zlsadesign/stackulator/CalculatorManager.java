package com.zlsadesign.stackulator;

import android.os.Bundle;
import android.util.Log;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class CalculatorManager {

  @JsonField(name = "calculator")
  Calculator calculator;

  @JsonField(name = "editing")
  boolean editing = false;

  @JsonField(name = "edit_value")
  StackValueEditor edit_value = new StackValueEditor();

  public CalculatorManager() {

  }

  public CalculatorManager(Calculator calculator) {
    this.calculator = calculator;
  }

  public void startEdit() throws CalculatorException {
    if(this.editing) return;

    StackValue value = this.calculator.pop();

    this.edit_value.setValue(value.toString());

    this.editing = true;
  }

  public void startNewEdit() {
    if(this.editing) return;

    this.edit_value.setValue("");

    this.editing = true;
  }

  private void finishEdit() {
    this.editing = false;
  }

  public void stopEdit() throws CalculatorException {
    if(!this.editing) return;

    if(!this.edit_value.isDirty()) { // if it hasn't been touched, discard it
      this.finishEdit();
      return;
    }

    if(this.edit_value.getValue().length() == 0) {
      this.finishEdit();
      return;
    }

    this.calculator.operation(this.edit_value.getValue());

    this.finishEdit();
  }

  public boolean isEditing() {
    return this.editing;
  }

  public void toggleEdit() throws CalculatorException {

    if(this.isEditing()) {
      this.stopEdit();
    } else {
      this.startEdit();
    }

  }

  public void append(String str) {
    this.startNewEdit();
    this.edit_value.append(str);
  }

  public void backspace() throws CalculatorException {
    this.startEdit();
    this.edit_value.backspace();
  }

  public void invert() throws CalculatorException {

    if(this.isEditing()) {
      this.edit_value.invert();
    } else {
      this.calculator.operation(Operation.INVERT);
    }

  }

  public void push() throws CalculatorException {
    if(this.isEditing()) {
      this.stopEdit();
    } else {
      this.operation(Operation.DUPLICATE);
    }
  }

  // Pass through these to the `Calculator`. Note that these will all attempt to push the current
  // editing value onto the stack and WILL throw an exception if the value is invalid.
  public void operation(Operation operation) throws CalculatorException {

    switch(operation.getOperation()) {
      case Operation.INVERT:
        this.invert();
        break;
      default:
        this.stopEdit();
        this.calculator.operation(operation);
        break;
    }

  }

  public void operation(int operation) throws CalculatorException {
    this.operation(new Operation(operation));
  }

  public void operation(String value) throws CalculatorException {
    this.operation(new Operation(Operation.PUSH, new StackValue(value)));
  }

  public int size() {
    int size = this.calculator.size();

    if(this.editing) {
      size += 1;
    }

    return size;

  }

  public AbstractStackValue get(int position) throws CalculatorException {

    if(position < this.calculator.size()) {
      return this.calculator.get(position);
    } else {
      return this.edit_value;
    }

  }

}
