package com.zlsadesign.stackulator;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {

  @BindView(R.id.toolbar)
  Toolbar toolbar;

  @BindView(R.id.info_version)
  TextView info_version;

  @Override
  protected void onCreate(Bundle state) {
    super.onCreate(state);

    setContentView(R.layout.activity_settings);

    ButterKnife.bind(this);

    this.initToolbar();

    this.initInfo();
  }

  private void initToolbar() {
    this.setSupportActionBar(this.toolbar);

    this.getSupportActionBar().setHomeButtonEnabled(true);
    this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  private void initInfo() {
    this.info_version.setText(BuildConfig.VERSION_NAME);
  }

}
