package com.zlsadesign.stackulator;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.bluelinelabs.logansquare.LoganSquare;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

  private CalculatorManager calculator_manager;

  @BindView(R.id.stack_frame)
  FrameLayout stack_frame;

  @BindView(R.id.keypad_frame)
  FrameLayout keypad_frame;

  private StackFragment stack_fragment;
  private KeypadFragment keypad_fragment;

  @Override
  protected void onCreate(Bundle state) {
    super.onCreate(state);

    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);

    if(state != null) {

      if(state.containsKey("calculator_manager")) {
        try {
          this.calculator_manager = LoganSquare.parse(state.getString("calculator_manager"), CalculatorManager.class);
        } catch(IOException e) {
          Log.w("MainActivity", "IOException while parsing\n" + e);
        }
      }

    } else {
      this.calculator_manager = new CalculatorManager(new Calculator());
    }

    this.initKeypad();
    this.initStack();
  }

  @Override
  public void onSaveInstanceState(Bundle out) {

    try {
      out.putString("calculator_manager", LoganSquare.serialize(this.calculator_manager));
    } catch(IOException e) {
      Log.w("MainActivity", "IOException while serializing\n" + e);
    }

    // call superclass to save any view hierarchy
    super.onSaveInstanceState(out);
  }

  private void initStack() {
    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction transaction = manager.beginTransaction();

    this.stack_fragment = new StackFragment();
    this.stack_fragment.setCalculatorManager(this.calculator_manager);

    transaction.replace(R.id.stack_frame, this.stack_fragment);
    transaction.commit();
  }

  private void initKeypad() {
    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction transaction = manager.beginTransaction();

    this.keypad_fragment = new KeypadFragment();
    this.keypad_fragment.setCalculatorManager(this.calculator_manager);

    transaction.replace(R.id.keypad_frame, this.keypad_fragment);
    transaction.commit();
  }

  public void onButtonClick(View view) {
    this.keypad_fragment.onButtonClick(view);

    this.stack_fragment.update();
  }

}
