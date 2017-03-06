package com.zlsadesign.stackulator;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StackAdapter extends RecyclerView.Adapter<StackAdapter.ViewHolder> {

  private CalculatorManager calculator_manager;

  public StackAdapter(CalculatorManager calculator_manager) {
    this.calculator_manager = calculator_manager;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stack, parent, false);

    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {

    try {
      holder.update(this.calculator_manager.get(position), position, this.getItemCount());
    } catch(CalculatorException e) {
      Log.w("StackAdapter", e);
    }

  }

  @Override
  public int getItemCount() {
    return this.calculator_manager.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    View view;

    AbstractStackValue value;

    @BindView(R.id.identifier)
    TextView identifier_text;

    @BindView(R.id.value)
    TextView value_text;

    public ViewHolder(View view) {
      super(view);

      this.view = view;

      ButterKnife.bind(this, view);
    }

    private void update(AbstractStackValue value, int position, int size) {
      String value_string = value.toString();

      //if(value_string.length() == 0) value_string = "0";

      this.value_text.setText(value_string);

      boolean is_top = (position == (size - 1));
      boolean is_top_minus_one = (position == (size - 2));

      int background_color = R.color.transparent;
      int text_color = R.color.stack_item_text;
      int identifier = R.string.identifier_none;

      if(is_top) {
        identifier = R.string.identifier_top;
        background_color = R.color.stack_item_top_background;
        text_color = R.color.stack_item_top_text;
      } else if(is_top_minus_one) {
        identifier = R.string.identifier_top_minus_one;
        background_color = R.color.stack_item_top_minus_one_background;
        text_color = R.color.stack_item_top_minus_one_text;
      }

      if(value instanceof StackValueEditor) {
        background_color = R.color.stack_item_editing_background;
        text_color = R.color.stack_item_editing_text;
      }

      if(value instanceof CalculatorManager.StackValueInProgress) {
        text_color = R.color.stack_item_in_progress_text;
        this.value_text.setText(R.string.value_operation_in_progress);
      }

      this.identifier_text.setText(identifier);

      this.view.setBackgroundColor(view.getContext().getResources().getColor(background_color));

      this.identifier_text.setTextColor(view.getContext().getResources().getColor(text_color));
      this.value_text.setTextColor(view.getContext().getResources().getColor(text_color));

    }

  }

}
