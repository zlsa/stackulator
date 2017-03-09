package com.zlsadesign.aboutpage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class InfoCardEntry implements View.OnClickListener {

  protected boolean clickable = false;

  protected String url = null;

  ViewGroup root_view;

  public View generate(ViewGroup container) {
    LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View view = inflater.inflate(this.getLayout(), container, false);

    this.root_view = (ViewGroup) view;

    if(this.url != null) {
      this.clickable = true;
    }

    this.setClickable(this.clickable);

    view.setOnClickListener(this);

    ButterKnife.bind(this, view);

    return view;
  }

  public void setClickable(boolean clickable) {
    this.clickable = clickable;

    this.root_view.setClickable(this.clickable);

    if(!this.clickable) {
      this.root_view.setBackground(null);
    } else {
      TypedValue typedValue = new TypedValue();
      this.root_view.getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);

      this.root_view.setBackgroundResource(typedValue.resourceId);
    }

  }

  public abstract int getLayout();

  @Override
  public void onClick(View v) {
    if(this.url != null) {
      Intent intent = new Intent(Intent.ACTION_VIEW);
      intent.setData(Uri.parse(this.url));

      Context context = this.root_view.getContext();

      if(intent.resolveActivity(context.getPackageManager()) != null) {
        context.startActivity(intent);
      } else {
        // No activity found to handle urls (wtf)
      }

    }
  }

  public static class Builder {

    protected InfoCardEntry entry;

    public InfoCardEntry build() {
      return this.entry;
    }

    public Builder withClickable(boolean clickable) {
      this.entry.clickable = clickable;
      return this;
    }

    public Builder withUrl(String url) {
      this.entry.url = url;
      return this;
    }

  }

}
