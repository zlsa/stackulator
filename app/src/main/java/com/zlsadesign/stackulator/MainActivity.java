package com.zlsadesign.stackulator;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

  private Calculator calculator = new Calculator();
  private CalculatorManager calculator_manager = new CalculatorManager(this.calculator);

  @BindView(R.id.stack_frame)
  FrameLayout stack_frame;

  @BindView(R.id.keypad_frame)
  FrameLayout keypad_frame;

  private StackFragment stack_fragment;
  private KeypadFragment keypad_fragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);

    this.initKeypad();
    this.initStack();
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
