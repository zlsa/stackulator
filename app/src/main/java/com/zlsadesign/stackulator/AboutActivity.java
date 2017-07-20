package com.zlsadesign.stackulator;

import com.zlsadesign.aboutpage.AppIconNameEntry;
import com.zlsadesign.aboutpage.AppVersionEntry;
import com.zlsadesign.aboutpage.HeaderEntry;
import com.zlsadesign.aboutpage.InfoCard;
import com.zlsadesign.aboutpage.InfoCardTwoLineEntry;
import com.zlsadesign.aboutpage.LibraryLicenseEntry;
import com.zlsadesign.aboutpage.SendFeedbackEntry;

public class AboutActivity extends com.zlsadesign.aboutpage.AboutActivity {

  protected int getStyle() {
    return STYLE_DARK;
  }

  protected void onSetup() {

    this.withToolbar(true);
    this.withToolbarUpButton(true);
    this.withToolbarFlat(true);
    //this.withToolbarColor(getResources().getColor(R.color.toolbar_background));
    //this.withStatusBarColor(getResources().getColor(R.color.black));

    this.addCard(
        new InfoCard.Builder(InfoCard.APP_INFO)

            .addEntry(
                new AppIconNameEntry.Builder()
                    .withIcon(R.mipmap.ic_launcher)
                    .withName(R.string.app_name)
                    .build())

            .addEntry(
                new AppVersionEntry.Builder()
                    .build())

            .addEntry(
                new SendFeedbackEntry.Builder()
                    .withEmailAddress("jonross.zlsa@gmail.com")
                    .withSubject("Feedback on Stackulator")
                    .build())

            .build()
    );

    this.addCard(
        new InfoCard.Builder(InfoCard.APP_LIBRARIES)

            .addEntry(
                new LibraryLicenseEntry.Builder()
                    .withName("Apfloat")
                    .withAuthor("Apfloat Contributors")
                    .withUrl("http://apfloat.org/apfloat_java/")
                    .withLicense(LibraryLicenseEntry.LICENSE_LGPL_21)
                    .build())

            .addEntry(
                new LibraryLicenseEntry.Builder()
                    .withName("ButterKnife")
                    .withAuthor("Jake Wharton")
                    .withUrl("http://jakewharton.github.io/butterknife/")
                    .withLicense(LibraryLicenseEntry.LICENSE_APACHE_20)
                    .build())

            .addEntry(
                new LibraryLicenseEntry.Builder()
                    .withName("LoganSquare")
                    .withAuthor("BlueLine Labs, Inc.")
                    .withUrl("https://github.com/bluelinelabs/LoganSquare")
                    .withLicense(LibraryLicenseEntry.LICENSE_APACHE_20)
                    .build())

            .build()
    );

    this.addCard(
        new InfoCard.Builder(InfoCard.AUTHOR_INFO)

            .addEntry(
                new HeaderEntry.Builder()
                    .withHeader(R.string.info_author)
                    .build())

            .addEntry(
                new InfoCardTwoLineEntry.Builder()
                    .withTitle("Jon Ross")
                    .withUrl("http://zlsadesign.com/")
                    .build())

            .addEntry(
                new InfoCardTwoLineEntry.Builder()
                    .withTitle("Autogyro")
                    .withIcon(R.drawable.ic_android)
                    .withUrl("https://zlsadesign.com/app/autogyro/")
                    .build())

            .addEntry(
                new InfoCardTwoLineEntry.Builder()
                    .withTitle("Speedometer")
                    .withIcon(R.drawable.ic_android)
                    .withUrl("https://zlsadesign.com/app/speedometer/")
                    .build())

            .build()
    );

  }

}
