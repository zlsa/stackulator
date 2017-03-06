package com.zlsadesign.stackulator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KeypadComponentFragment extends Fragment implements View.OnClickListener {

  private KeypadFragment fragment;

  @BindView(R.id.keypad)
  View keypad;

  @BindView(R.id.scrim)
  View scrim;

  private int position = 0;

  public KeypadComponentFragment() {

  }

  public KeypadComponentFragment(KeypadFragment fragment, int position) {
    this.fragment = fragment;
    this.position = position;
  }

  public void setEnabled(boolean enabled) {
    this.scrim.setClickable(!enabled);
  }

  private int getLayout() {
    if(this.position == 0) {
      return R.layout.view_keypad_primary_wrapper;
    } else {
      return R.layout.view_keypad_secondary_wrapper;
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

    ButterKnife.bind(this, view);

    this.keypad.setOnClickListener(this);
    this.scrim.setOnClickListener(this);

    return view;
  }

  @Override
  public void onClick(View v) {
    this.fragment.swap();
  }

}
