package com.zlsadesign.aboutpage;

import android.content.pm.PackageManager;

public class AppVersionEntry extends InfoCardTwoLineEntry {

  protected String app_version = null;

  protected void populate() {

    this.icon_view.setImageResource(R.drawable.aboutpage_ic_info);
    this.title_view.setText(R.string.aboutpage_info_app_version);

    String version_name = "";

    if(this.app_version != null) {
      version_name = this.app_version;
    } else {
      version_name = "???";
    }

    this.secondary_view.setText(version_name);

  }

  public static class Builder extends InfoCardEntry.Builder {

    public Builder() {
      this.entry = new AppVersionEntry();
    }

    public Builder withVersion(String app_version) {
      ((AppVersionEntry) this.entry).app_version = app_version;
      return this;
    }

  }

}
