package com.zlsadesign.aboutpage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HeaderEntry extends InfoCardEntry {

  protected int header_resource = -1;
  protected String header = null;

  @BindView(R2.id.aboutpage_entry_header)
  TextView header_view;

  @Override
  public View generate(ViewGroup container) {
    View view = super.generate(container);

    if(this.header_resource != -1) {
      this.header = this.root_view.getResources().getString(this.header_resource);
    }

    this.header_view.setText(this.header);

    return view;
  }

  @Override
  public int getLayout() {
    return R.layout.aboutpage_header_entry;
  }

  public static class Builder extends InfoCardEntry.Builder {

    public Builder() {
      this.entry = new HeaderEntry();
    }

    public Builder withHeader(String header) {
      ((HeaderEntry) this.entry).header = header;
      return this;
    }

    public Builder withHeader(int header_resource) {
      ((HeaderEntry) this.entry).header_resource = header_resource;
      return this;
    }

  }

}
