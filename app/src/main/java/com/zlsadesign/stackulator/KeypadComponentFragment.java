package com.zlsadesign.stackulator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class KeypadComponentFragment extends Fragment {

  private int position = 0;

  public KeypadComponentFragment() {

  }

  public KeypadComponentFragment(int position) {
    this.position = position;
  }

  private int getLayout() {
    if(this.position == 0) {
      return R.layout.view_keypad_primary;
    } else {
      return R.layout.view_keypad_secondary;
    }
  }

  @Override
  public void onSaveInstanceState(Bundle state) {
    state.putInt("position", this.position);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {

    if(state != null) {
      if(state.containsKey("position")) {
        this.position = state.getInt("position", 0);
      }
    }

    ViewGroup view = (ViewGroup) inflater.inflate(this.getLayout(), container, false);

    return view;
  }

}
