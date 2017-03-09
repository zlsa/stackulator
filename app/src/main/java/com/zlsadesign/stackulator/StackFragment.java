package com.zlsadesign.stackulator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.PopupMenu;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StackFragment extends Fragment {

  private CalculatorManager calculator_manager;

  @BindView(R.id.root_view)
  ViewGroup root_view;

  @BindView(R.id.recyclerview)
  RecyclerView recyclerview;

  @BindView(R.id.snackbar_error_message)
  TextView snackbar_error;

  LinearLayoutManager layoutmanager;

  private StackAdapter adapter;

  private boolean snackbar_hidden = false;
  private long last_error_time;

  public StackFragment() {
  }

  public void setCalculatorManager(CalculatorManager calculator_manager) {
    this.calculator_manager = calculator_manager;
  }

  @OnClick(R.id.menu_button)
  public void onMenuButtonClick(View v) {
    PopupMenu popup = new PopupMenu(getContext(), v);

    popup.getMenuInflater().inflate(R.menu.menu_main, popup.getMenu());
    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

      public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
          case R.id.about:
            Intent intent = new Intent(getContext(), AboutActivity.class);
            startActivity(intent);
            break;
        }

        return true;
      }

    });

    popup.show();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
    View view = inflater.inflate(R.layout.fragment_stack, container, false);

    ButterKnife.bind(this, view);

    this.hideError(false);

    this.initRecyclerView();

    return view;
  }

  private void initRecyclerView() {
    this.layoutmanager = new LinearLayoutManager(getContext());
    this.layoutmanager.setStackFromEnd(true);

    this.recyclerview.setLayoutManager(this.layoutmanager);

    // specify an adapter (see also next example)
    this.adapter = new StackAdapter(this.calculator_manager);
    this.recyclerview.setAdapter(this.adapter);

  }

  public void update() {

    if((System.currentTimeMillis() - this.last_error_time) > 0.2 * 1000) {
      this.hideError(true);
    }

    this.adapter.notifyDataSetChanged();

    this.recyclerview.scrollToPosition(this.adapter.getItemCount() - 1);
  }

  public void setError(CalculatorException e) {
    if(e == null) {
      this.hideError(true);
      return;
    }

    this.snackbar_error.setText(e.toString(getContext()));

    if(this.snackbar_hidden) {
      this.snackbar_hidden = false;
      this.snackbar_error.animate().translationY(0).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(150);
    }

    this.scheduleHide(2250);

    this.last_error_time = System.currentTimeMillis();
  }

  public void scheduleHide(final int delay) {

    Handler handler = new Handler();
    Runnable runnable = new Runnable(){
      public void run() {
        if((System.currentTimeMillis() - last_error_time) > delay) {
          hideError(true);
        }
      }
    };

    handler.postDelayed(runnable, delay);
  }

  public void hideError(boolean animate) {
    DisplayMetrics metrics = getResources().getDisplayMetrics();

    if(!this.snackbar_hidden) {
      this.snackbar_hidden = true;
      if(animate) {
        this.snackbar_error.animate().translationY(48f * metrics.density).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(150);
      } else {
        this.snackbar_error.animate().translationY(48f * metrics.density).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(0);
      }
    }

  }

}

