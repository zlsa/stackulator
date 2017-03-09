package com.zlsadesign.aboutpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoCard {

  public static final int APP_INFO = 0;
  public static final int AUTHOR_INFO = 1;
  public static final int APP_LIBRARIES = 2;

  protected int type;

  @BindView(R2.id.aboutpage_info_card)
  LinearLayout card;

  protected List<InfoCardEntry> entries = new ArrayList<>();

  public View generate(AboutActivity aboutpage, ViewGroup container) {
    LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    View view = inflater.inflate(R.layout.aboutpage_info_card, container, false);

    ButterKnife.bind(this, view);

    if(aboutpage.style == AboutActivity.STYLE_DARK) {
      this.card.setBackgroundResource(R.drawable.aboutpage_info_card_dark);
    } else {
      this.card.setBackgroundResource(R.drawable.aboutpage_info_card_light);
    }

    for(InfoCardEntry entry : this.entries) {
      this.card.addView(entry.generate(this.card));
    }

    return view;
  }

  public static class Builder {

    protected InfoCard card = new InfoCard();

    public Builder(int type) {
      this.card.type = type;

      if(type == APP_LIBRARIES) {
        this.addEntry(
            new HeaderEntry.Builder()
                .withHeader(R.string.aboutpage_info_libraries)
                .build());
      }

    }

    public Builder addEntry(InfoCardEntry entry) {
      this.card.entries.add(entry);
      return this;
    }

    public InfoCard build() {
      return this.card;
    }

  }

}
