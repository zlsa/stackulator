package com.zlsadesign.aboutpage;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

public class AppIconNameEntry extends InfoCardEntry {

  protected int app_name_resource = -1;
  protected String app_name = null;

  protected int app_icon_resource = -1;

  @BindView(R2.id.com_zlsadesign_about_app_icon)
  ImageView app_icon_view;

  @BindView(R2.id.com_zlsadesign_about_app_name)
  TextView app_name_view;

  @Override
  public View generate(ViewGroup container) {
    View view = super.generate(container);

    this.app_icon_view.setImageResource(this.app_icon_resource);

    if(this.app_name != null) {
      this.app_name_view.setText(this.app_name);
    } else if(this.app_name_resource != -1) {
      this.app_name_view.setText(this.app_name_resource);
    }

    return view;
  }


  @Override
  public int getLayout() {
    return R.layout.aboutpage_app_icon_name_entry;
  }

  public static class Builder extends InfoCardEntry.Builder {

    public Builder() {
      this.entry = new AppIconNameEntry();
    }

    public Builder withIcon(int app_icon_resource) {
      ((AppIconNameEntry) this.entry).app_icon_resource = app_icon_resource;
      return this;
    }

    public Builder withName(String app_name) {
      ((AppIconNameEntry) this.entry).app_name = app_name;
      return this;
    }

    public Builder withName(int app_name_resource) {
      ((AppIconNameEntry) this.entry).app_name_resource = app_name_resource;
      return this;
    }

  }

}
