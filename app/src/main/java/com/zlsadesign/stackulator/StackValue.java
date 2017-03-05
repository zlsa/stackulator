package com.zlsadesign.stackulator;

import android.util.Log;

import org.apfloat.Apfloat;

public class StackValue {

  private Apfloat value;

  public StackValue(Apfloat value) {
    this.setValue(value);
  }

  public StackValue(String value) throws CalculatorException {
    try {
      this.setValue(new Apfloat(value, 128));
    } catch(NumberFormatException e) {
      throw new CalculatorException(CalculatorException.BAD_NUMBER);
    }

  }

  public void setValue(Apfloat value) {
    this.value = value;
  }

  public Apfloat getValue() {
    return this.value;
  }

}
