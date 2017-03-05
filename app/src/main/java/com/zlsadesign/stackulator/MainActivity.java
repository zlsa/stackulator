package com.zlsadesign.stackulator;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

  private Calculator calculator = new Calculator();

  @BindView(R.id.keypad_frame)
  FrameLayout keypad_frame;

  private KeypadFragment keypad_fragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);

    this.initKeypad();

    try {
      Operation pushnumber = new Operation(Operation.PUSH, "42");

      this.calculator.operation(pushnumber);
      this.calculator.operation(pushnumber);

      this.calculator.operation(Operation.MULTIPLY);
    } catch(CalculatorException e) {
      e.printStackTrace();
    }

  }

  private void initKeypad() {
    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction transaction = manager.beginTransaction();

    this.keypad_fragment = new KeypadFragment();
    transaction.replace(R.id.keypad_frame, this.keypad_fragment);
    transaction.commit();
  }

}
