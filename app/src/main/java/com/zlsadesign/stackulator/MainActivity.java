package com.zlsadesign.stackulator;

import android.content.Context;
import android.content.SharedPreferences;
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

public class MainActivity extends AppCompatActivity implements CalculatorListener {

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

    this.applyTheme();

    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);

    this.calculator_manager = this.restoreCalculatorManager();

    this.calculator_manager.calculator.setListener(this);

    this.initKeypad();
    this.initStack();
  }

  private void applyTheme() {
    //setTheme(R.style.AppTheme_Light);
  }

  protected void onStop() {
    super.onStop();

    this.calculator_manager.destroy();
  }

  private void saveCalculatorManager() {
    String json = null;

    try {
      json = LoganSquare.serialize(this.calculator_manager);
    } catch(IOException ignored) {
      return;
    }

    SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = prefs.edit();
    editor.putString("calculator_manager", json);
    editor.apply();
  }

  private CalculatorManager restoreCalculatorManager() {

    SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);

    if(prefs.contains("calculator_manager")) {
      try {
        return LoganSquare.parse(prefs.getString("calculator_manager", null), CalculatorManager.class);
      } catch(IOException ignored) {

      }
    }

    return new CalculatorManager(new Calculator());
  }

  @Override
  public void onSaveInstanceState(Bundle out) {
    this.saveCalculatorManager();

    super.onSaveInstanceState(out);
  }

  private void initStack() {
    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction transaction = manager.beginTransaction();

    this.stack_fragment = new StackFragment();
    this.stack_fragment.setCalculatorManager(this.calculator_manager);
    this.stack_fragment.setMainActivity(this);

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

    this.update();
  }

  public void setError(CalculatorException e) {
    this.stack_fragment.setError(e);
  }

  public void update() {
    this.stack_fragment.update();
  }

  @Override
  public void onStackChanged() {

    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        update();
      }
    });

  }

  @Override
  public void onCalculatorException(final CalculatorException e) {

    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        update();
        setError(e);
      }
    });

  }

  public void toggleTheme() {
    Log.d("MainActivity", "toggling theme");
  }

}
