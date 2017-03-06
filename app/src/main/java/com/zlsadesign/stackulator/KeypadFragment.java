package com.zlsadesign.stackulator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KeypadFragment extends Fragment {

  private static String NUMBERS = "0123456789";

  @BindView(R.id.viewpager)
  ViewPager pager;

  KeypadAdapter adapter;

  private CalculatorManager calculator_manager;

  public void setCalculatorManager(CalculatorManager calculator_manager) {
    this.calculator_manager = calculator_manager;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
    View view = inflater.inflate(R.layout.fragment_keypad, container, false);

    ButterKnife.bind(this, view);

    this.initViewPager();

    return view;
  }

  private void initViewPager() {
    this.adapter = new KeypadAdapter(getFragmentManager());
    this.pager.setAdapter(this.adapter);
    this.pager.setPageTransformer(false, new KeypadTransformer());
  }

  public void onButtonClick(View view) {
    String tag = view.getTag().toString();

    try {
      this.handleButton(tag);
    } catch(CalculatorException e) {
      ((MainActivity) this.getActivity()).setError(e);
    }

  }

  private void handleButton(String tag) throws CalculatorException {

    int type = Operation.toOperationType(tag);

    switch(type) {

      case Operation.NONE:
      case Operation.PUSH:
        this.handleEdit(tag);
        break;

      default:
        this.calculator_manager.operation(tag);

    }

  }

  private void handleEdit(String tag) throws CalculatorException {

    switch(tag) {
      case "push":
        this.calculator_manager.push();
        return;
      case "backspace":
        this.calculator_manager.backspace();
        return;
      case "edit":
        this.calculator_manager.toggleEdit();
        return;
      case ".":
        this.calculator_manager.append(".");
        return;
      case "invert":
        this.calculator_manager.invert();
        return;
    }

    if(NUMBERS.contains(tag)) {
      this.calculator_manager.append(tag);
    } else {
      this.calculator_manager.operation(tag);
    }

  }

  private class KeypadAdapter extends FragmentStatePagerAdapter {

    public KeypadAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      return new KeypadComponentFragment(position);
    }

    @Override
    public int getCount() {
      return 2;
    }

    @Override
    public float getPageWidth(int position) {
      if(position == 1) return (float) 0.75;
      return (float) 0.95;
    }

  }

  private static class KeypadTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View view, float position) {

      if(position <= 0f) {
        view.setTranslationX(view.getWidth() * (1 / 0.95f) * -position);
        view.setAlpha(Math.max(1.0f + (position * 0.7f), 0.0f));
      } else {
        view.setTranslationX(0.0f);
        view.setAlpha(1.0f);
      }

    }

  }

}

