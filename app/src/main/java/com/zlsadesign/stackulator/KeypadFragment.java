package com.zlsadesign.stackulator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KeypadFragment extends Fragment {

  @BindView(R.id.viewpager)
  ViewPager pager;

  KeypadAdapter adapter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
    View view = inflater.inflate(R.layout.fragment_keypad, container, false);

    ButterKnife.bind(this, view);

    this.initViewPager();

    return view;
  }

  private void initViewPager() {
    this.adapter = new KeypadAdapter(getFragmentManager());
    this.pager.setAdapter(this.adapter);
    this.pager.setPageTransformer(false, new KeypadTransformer());

    //this.viewpager.;
    /*
    this.pane_layout.setSliderFadeColor(getResources().getColor(R.color.transparent));
    this.pane_layout.setCoveredFadeColor(getResources().getColor(R.color.accent));
     */
  }

  private class KeypadAdapter extends FragmentStatePagerAdapter {

    public KeypadAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      return new KeypadComponentFragment(position);
    }

    @Override
    public int getCount() {
      return 2;
    }

    @Override
    public float getPageWidth(int position) {
      return (float) 0.95;
    }

  }

  private static class KeypadTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View view, float position) {

      if(position <= 0f) {
        view.setTranslationX(view.getWidth() * (1 / 0.95f) * -position);
        view.setAlpha(Math.max(1.0f + (position * 0.7f), 0.0f));
      } else {
        view.setTranslationX(0.0f);
        view.setAlpha(1.0f);
      }

    }

  }
}

