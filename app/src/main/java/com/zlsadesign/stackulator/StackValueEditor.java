package com.zlsadesign.stackulator;

import org.apfloat.Apfloat;

public class StackValueEditor extends AbstractStackValue {

  private String value = "";
  private boolean dirty = false;

  public StackValueEditor() {
  }

  public StackValueEditor(String value) {
    this.setValue(value);
  }

  public StackValueEditor(AbstractStackValue value) {
    this.setValue(value);
  }

  public void reset() {
    this.dirty = false;
    this.value = "";
  }

  public void setValue(String value) {
    this.value = value;
    this.dirty = true;
  }

  public void setValue(AbstractStackValue value) {
    this.setValue(value.toString());
  }

  public boolean isDirty() {
    return this.dirty;
  }

  public String getValue() {
    return this.value;
  }

  public boolean isValid() {

    try {
      StackValue value = new StackValue(this.value);
    } catch(CalculatorException e) {
      return false;
    }

    return true;

  }

  public String toString() {
    return this.value;
  }

  public void append(String str) {
    this.value += str;
  }

  public void backspace() {

    if(this.value.length() >= 1) {
      this.value = this.value.substring(0, this.value.length() - 1);
    }

  }

  public void invert() {
    if(this.value.startsWith("-")) {
      this.value = this.value.substring(1, this.value.length());
    } else {
      this.value = "-" + this.value;
    }
  }

}
