package com.zlsadesign.aboutpage;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.View;

public class LibraryLicenseEntry extends InfoCardTwoLineEntry {

  public static final int LICENSE_APACHE_20 = R.string.aboutpage_license_apache_20_name;
  public static final int LICENSE_LGPL_21 = R.string.aboutpage_license_lgpl_21_name;

  protected String library_name = null;
  protected String library_url = null;
  protected String library_author = null;

  protected String library_license = null;
  protected int library_license_resource = -1;

  protected void populate() {

    this.setClickable(true);

    Resources resources = this.root_view.getResources();

    this.icon_view.setImageResource(R.drawable.aboutpage_ic_launch_external);
    this.title_view.setText(resources.getString(R.string.aboutpage_library_entry_title, this.library_name, this.library_author));

    if(this.library_license_resource != -1) {
      this.library_license = resources.getString(this.library_license_resource);
    }

    this.secondary_view.setText(this.library_license);
  }

  @Override
  public void onClick(View v) {

    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setData(Uri.parse(this.library_url));

    Context context = this.root_view.getContext();

    if(intent.resolveActivity(context.getPackageManager()) != null) {
      context.startActivity(intent);
    } else {
      // No activity found to handle urls (wtf)
    }

  }

  public static class Builder extends InfoCardEntry.Builder {

    public Builder() {
      this.entry = new LibraryLicenseEntry();
    }

    public Builder withName(String library_name) {
      ((LibraryLicenseEntry) this.entry).library_name = library_name;
      return this;
    }

    public Builder withAuthor(String library_author) {
      ((LibraryLicenseEntry) this.entry).library_author = library_author;
      return this;
    }

    public Builder withUrl(String library_url) {
      ((LibraryLicenseEntry) this.entry).library_url = library_url;
      return this;
    }

    public Builder withLicense(String library_license) {
      ((LibraryLicenseEntry) this.entry).library_license = library_license;
      return this;
    }

    public Builder withLicense(int library_license_resource) {
      ((LibraryLicenseEntry) this.entry).library_license_resource = library_license_resource;
      return this;
    }

  }

}
