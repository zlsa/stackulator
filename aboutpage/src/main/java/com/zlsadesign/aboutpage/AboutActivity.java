package com.zlsadesign.aboutpage;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class AboutActivity extends Activity {

  public static final int STYLE_LIGHT = 0;
  public static final int STYLE_DARK = 1;

  int style = STYLE_LIGHT;

  // ## Toolbar

  // Enables/disables the toolbar (app bar).
  boolean has_toolbar = false;

  // Makes the toolbar flat (no elevation and transparent).
  boolean has_toolbar_flat = false;

  // Up button (left-pointing arrow) on toolbar.
  boolean has_toolbar_up_button = false;

  // Toolbar color, as a color int (NOT a resource!)
  @ColorInt
  int toolbar_color = -1;

  // Toolbar color, as a color resource
  int toolbar_color_resource = -1;

  // ## Status bar

  // Status bar color, as a color int (NOT a resource!)
  @ColorInt
  int status_bar_color = -1;

  // Status bar color, as a color resource
  int status_bar_color_resource = -1;

  // # View binding

  @BindView(R2.id.aboutpage_card_list)
  LinearLayout card_list;

  @BindView(R2.id.aboutpage_toolbar)
  Toolbar toolbar;

  @Override
  protected void onCreate(Bundle state) {
    super.onCreate(state);

    this.style = this.getStyle();

    int style = R.style.AboutPage_Light;

    if(this.style == STYLE_DARK) {
      style = R.style.AboutPage_Dark;
    }

    super.setTheme(style);

    this.setContentView(R.layout.aboutpage_activity_about);

    ButterKnife.bind(this);

    this.onSetup();

    this.initToolbar();
    this.initStatusBar();
  }

  protected abstract void onSetup();

  protected int getStyle() {
    return STYLE_LIGHT;
  }

  private void initToolbar() {
    this.setActionBar(this.toolbar);

    if(this.has_toolbar_up_button) {
      this.getActionBar().setHomeButtonEnabled(true);
      this.getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    if(this.has_toolbar_flat) {
      this.toolbar.setBackgroundColor(this.getResources().getColor(android.R.color.transparent));
      //this.toolbar.setElevation(0);
    }

    // Set the toolbar color.

    int color_resource = -1;
    int color = -1;

    if(!this.has_toolbar_flat) {

      if(this.toolbar_color_resource != -1) {
        color_resource = this.toolbar_color_resource;
      }

      if(this.toolbar_color != -1) {
        color = this.toolbar_color;
      }

      if(color_resource != -1) {
        color = this.getResources().getColor(color_resource);
      }

      if(color != -1) {
        this.toolbar.setBackgroundColor(color);
      }

    }

    if(!this.has_toolbar) {
      this.getActionBar().hide();
    }

  }

  private void initStatusBar() {

    Window window = this.getWindow();

    int color_resource = -1;
    int color = -1;

    if(this.has_toolbar_flat) {
      color_resource = android.R.color.transparent;
    }

    if(this.status_bar_color_resource != -1) {
      color_resource = this.status_bar_color_resource;
    }

    if(this.status_bar_color != -1) {
      color = this.status_bar_color;
    }

    if(color_resource != -1) {
      color = this.getResources().getColor(color_resource);
    }

    if(color != -1) {
      window.setStatusBarColor(color);
    }

  }

  // ## Toolbar

  public void withToolbar(boolean has_toolbar) {
    this.has_toolbar = has_toolbar;
  }

  public void withToolbarFlat(boolean has_toolbar_flat) {
    this.has_toolbar_flat = has_toolbar_flat;
  }

  public void withToolbarUpButton(boolean has_toolbar_up_button) {
    this.has_toolbar_up_button = has_toolbar_up_button;
  }

  public void withToolbarColor(@ColorInt int toolbar_color) {
    this.toolbar_color = toolbar_color;
  }

  public void withToolbarColorResource(int toolbar_color_resource) {
    this.toolbar_color_resource = toolbar_color_resource;
  }

  // ## Statusbar

  public void withStatusBarColor(@ColorInt int status_bar_color) {
    this.status_bar_color = status_bar_color;
  }

  public void withStatusBarColorResource(int status_bar_color_resource) {
    this.status_bar_color_resource = status_bar_color_resource;
  }

  // # Cards

  public void addCard(InfoCard card) {
    this.card_list.addView(card.generate(this, this.card_list));
  }

}


