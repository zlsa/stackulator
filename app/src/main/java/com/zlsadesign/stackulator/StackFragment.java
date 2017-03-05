package com.zlsadesign.stackulator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StackFragment extends Fragment {

  private CalculatorManager calculator_manager;

  @BindView(R.id.recyclerview)
  RecyclerView recyclerview;

  LinearLayoutManager layoutmanager;

  private StackAdapter adapter;

  public void setCalculatorManager(CalculatorManager calculator_manager) {
    this.calculator_manager = calculator_manager;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
    View view = inflater.inflate(R.layout.fragment_stack, container, false);

    ButterKnife.bind(this, view);

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
    this.adapter.notifyDataSetChanged();

    this.recyclerview.scrollToPosition(this.adapter.getItemCount() - 1);
  }

}

