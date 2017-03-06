package com.zlsadesign.stackulator;

public interface CalculatorListener {

  void onStackChanged();

  void onCalculatorException(CalculatorException e);
}
