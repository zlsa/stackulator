package com.zlsadesign.aboutpage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SendFeedbackEntry extends InfoCardOneLineEntry {

  protected List<String> email_addresses = new ArrayList<>();
  protected String subject = null;

  protected void populate() {
    this.icon_view.setImageResource(R.drawable.aboutpage_ic_email);
    this.title_view.setText(R.string.aboutpage_action_send_feedback);
  }

  @Override
  public void onClick(View v) {

    if(this.subject == null || this.email_addresses.size() == 0) {
      // No subject or email addresses
      return;
    }

    String[] email_addresses = new String[this.email_addresses.size()];
    email_addresses = this.email_addresses.toArray(email_addresses);

    Intent intent = new Intent(Intent.ACTION_SENDTO);
    intent.setData(Uri.parse("mailto:"));
    intent.putExtra(Intent.EXTRA_EMAIL, email_addresses);
    intent.putExtra(Intent.EXTRA_SUBJECT, this.subject);

    Context context = this.root_view.getContext();

    if(intent.resolveActivity(context.getPackageManager()) != null) {
      context.startActivity(intent);
    } else {
      // No activity found to handle emails
    }

  }

  public static class Builder extends InfoCardEntry.Builder {

    public Builder() {
      this.entry = new SendFeedbackEntry();

      this.withClickable(true);
    }

    public Builder withEmailAddress(String email_address) {
      ((SendFeedbackEntry) this.entry).email_addresses.add(email_address);
      return this;
    }

    public Builder withSubject(String subject) {
      ((SendFeedbackEntry) this.entry).subject = subject;
      return this;
    }

  }

}
