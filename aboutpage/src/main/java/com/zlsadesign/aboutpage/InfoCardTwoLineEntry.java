package com.zlsadesign.aboutpage;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

public class InfoCardTwoLineEntry extends InfoCardEntry {

  protected int icon_resource = -1;
  protected String title = null;
  protected String secondary = null;

  @BindView(R2.id.aboutpage_entry_icon)
  ImageView icon_view;

  @BindView(R2.id.aboutpage_entry_title)
  TextView title_view;

  @BindView(R2.id.aboutpage_entry_secondary)
  TextView secondary_view;

  @Override
  public View generate(ViewGroup container) {
    View view = super.generate(container);

    this.populate();

    return view;
  }

  protected void populate() {
    if(this.url != null && this.secondary == null) {
      this.secondary = this.url;
    }

    if(this.icon_resource != -1)
      this.icon_view.setImageResource(this.icon_resource);

    if(this.title != null)
      this.title_view.setText(this.title);

    if(this.secondary != null)
      this.secondary_view.setText(this.secondary);
  }

  @Override
  public int getLayout() {
    return R.layout.aboutpage_two_line_entry;
  }

  public static class Builder extends InfoCardEntry.Builder {

    public Builder() {
      this.entry = new InfoCardTwoLineEntry();
    }

    public Builder withIcon(int icon_resource) {
      ((InfoCardTwoLineEntry) this.entry).icon_resource = icon_resource;
      return this;
    }

    public Builder withTitle(String title) {
      ((InfoCardTwoLineEntry) this.entry).title = title;
      return this;
    }

    public Builder withSecondary(String secondary) {
      ((InfoCardTwoLineEntry) this.entry).secondary = secondary;
      return this;
    }

  }

}
