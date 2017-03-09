package com.zlsadesign.aboutpage;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

public class InfoCardOneLineEntry extends InfoCardEntry {

  protected int icon_resource = -1;
  protected String title = null;

  @BindView(R2.id.aboutpage_entry_icon)
  ImageView icon_view;

  @BindView(R2.id.aboutpage_entry_title)
  TextView title_view;

  @Override
  public View generate(ViewGroup container) {
    View view = super.generate(container);

    this.populate();

    return view;
  }

  protected void populate() {
    if(this.icon_resource != -1)
      this.icon_view.setImageResource(this.icon_resource);

    if(this.title != null)
      this.title_view.setText(this.title);
  }

  @Override
  public int getLayout() {
    return R.layout.aboutpage_one_line_entry;
  }

  public static class Builder extends InfoCardEntry.Builder {

    public Builder() {
      this.entry = new InfoCardOneLineEntry();
    }

    public Builder withIcon(int icon_resource) {
      ((InfoCardOneLineEntry) this.entry).icon_resource = icon_resource;
      return this;
    }

    public Builder withTitle(String title) {
      ((InfoCardOneLineEntry) this.entry).title = title;
      return this;
    }

  }

}
