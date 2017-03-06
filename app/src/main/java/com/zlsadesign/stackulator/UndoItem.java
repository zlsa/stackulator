package com.zlsadesign.stackulator;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.ArrayList;
import java.util.List;

@JsonObject
public class UndoItem {

  @JsonField(name = "operations")
  List<StackValue> stack = new ArrayList<>();

  public UndoItem() {

  }

  public void set(List<StackValue> stack) {
    this.stack = new ArrayList<>(stack);
  }

}
