package com.zlsadesign.stackulator;

import com.bluelinelabs.logansquare.typeconverters.StringBasedTypeConverter;

import org.apfloat.Apfloat;

public class ApfloatConverter extends StringBasedTypeConverter<Apfloat> {

  @Override
  public Apfloat getFromString(String s) {
    return new Apfloat(s);
  }

  public String convertToString(Apfloat object) {
    return object.toString();
  }

}
