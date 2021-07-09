package tech.zekon.gui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AppRate {
    public static void Start(Context mContext, String AppTitle, String PackageName, int DaysUntilPrompt, int LaunchesUntilPrompt) {
        SharedPreferences prefs = mContext.getSharedPreferences("AppRate", 0);
        if (prefs.getBoolean("dontshowagain", false)) {
            return;
        }

        SharedPreferences.Editor editor = prefs.edit();

        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        // Get date of first launch
        long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }

        // Wait at least n days before opening
        //if (launch_count >= LaunchesUntilPrompt) {
           // if (System.currentTimeMillis() >= date_firstLaunch + (DaysUntilPrompt * 24 * 60 * 60 * 1000)) {
                showRateDialog(mContext, editor, AppTitle, PackageName);
          //  }
       // }
        editor.apply();
    }

    public static void showRateDialog(final Context mContext, final SharedPreferences.Editor editor, String AppTitle, String PackageName) {
        final Dialog dialog = new Dialog(mContext);
        dialog.setTitle("Rate " + AppTitle);

        LinearLayout ll = new LinearLayout(mContext);
        ll.setOrientation(LinearLayout.VERTICAL);

        TextView tv = new TextView(mContext);
        tv.setText("If you enjoy using " + AppTitle + ", please take a moment to rate it. Thanks for your support!");
        tv.setWidth(240);
        tv.setPadding(4, 0, 4, 10);
        ll.addView(tv);

        Button b1 = new Button(mContext);
        b1.setText("Rate " + AppTitle);
        b1.setOnClickListener(v -> {
            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PackageName)));
            dialog.dismiss();
        });
        ll.addView(b1);

        Button b2 = new Button(mContext);
        b2.setText("Remind me later");
        b2.setOnClickListener(v -> dialog.dismiss());
        ll.addView(b2);

        Button b3 = new Button(mContext);
        b3.setText("No, thanks");
        b3.setOnClickListener(v -> {
            if (editor != null) {
                editor.putBoolean("dontshowagain", true);
                editor.commit();
            }
            dialog.dismiss();
        });
        ll.addView(b3);

        dialog.setContentView(ll);
        dialog.show();
    }
}
