package com.zlsadesign.stackulator;

import android.content.Context;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.util.Log;

import java.lang.reflect.Field;

public class OverhangSlidingPaneLayout extends SlidingPaneLayout {

  protected int mOverhangSize = 16;

  public OverhangSlidingPaneLayout(Context context) {
    super(context);

    this.setOverhang();
  }

  public OverhangSlidingPaneLayout(Context context, AttributeSet attrs) {
    super(context, attrs);

    this.setOverhang();
  }

  public OverhangSlidingPaneLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

    this.setOverhang();
  }

  private void setOverhang() {

    final float density = getContext().getResources().getDisplayMetrics().density;

    Field f = null;
    try {
      f = SlidingPaneLayout.class.getDeclaredField("mOverhangSize");
    } catch(NoSuchFieldException e) {
      Log.w("foo", "NoSuchFieldException");
      return;
    }

    f.setAccessible(true);

    try {
      f.setInt(this, (int) (this.mOverhangSize * density));
    } catch(IllegalAccessException e) {
      Log.w("foo", "IllegalAccessException");
      return;
    }

    this.openPane();

  }

}
