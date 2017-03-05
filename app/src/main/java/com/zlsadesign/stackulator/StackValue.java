package com.zlsadesign.stackulator;

import android.util.Log;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import org.apfloat.Apfloat;

@JsonObject
public class StackValue extends AbstractStackValue {

  @JsonField(name = "stack", typeConverter = ApfloatConverter.class)
  private Apfloat value;

  public StackValue() {
  }

  public StackValue(Apfloat value) {
    this.setValue(value);
  }

  public StackValue(String value) throws CalculatorException {
    this.setValue(value);
  }

  public void setValue(Apfloat value) {
    this.value = value;
  }

  public void setValue(String value) throws CalculatorException {

    try {
      this.setValue(new Apfloat(value, 32));
    } catch(NumberFormatException e) {
      throw new CalculatorException(CalculatorException.BAD_NUMBER);
    }

  }

  public Apfloat getValue() {
    return this.value;
  }

  public String toString() {
    String value = this.value.precision(6).toString(true);

    if(value.length() > 12) {
      return this.value.precision(6).toString();
    }

    return value;

  }

}
