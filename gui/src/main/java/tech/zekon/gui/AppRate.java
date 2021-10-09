package tech.zekon.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

public class AppRate {
    private static boolean AlreadyRan = false;

    public static void Start(Activity mContext, int theme, String AppTitle, String PackageName, int DaysUntilPrompt, int LaunchesUntilPrompt) {
        if (AlreadyRan) return;
        AlreadyRan = true;
        SharedPreferences prefs = mContext.getSharedPreferences("AppRate", 0);
        if (prefs.getBoolean("dontshowagain", false)) return;

        SharedPreferences.Editor editor = prefs.edit();

        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }
        editor.apply();

        if (launch_count >= LaunchesUntilPrompt || System.currentTimeMillis() >= date_firstLaunch + ((long) DaysUntilPrompt * 24 * 60 * 60 * 1000)) {
            showRateDialog(mContext, theme, AppTitle, PackageName);
        }
    }

    public static void showRateDialog(final Activity mContext, int theme, String AppTitle, String PackageName) {
        SharedPreferences prefs = mContext.getSharedPreferences("AppRate", 0);
        SharedPreferences.Editor editor = prefs.edit();

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, theme);
        builder.setMessage("If you enjoy using " + AppTitle + ", please take a moment to rate it. Thanks for your support!")
                .setPositiveButton("Rate", (dialog, id) -> {
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PackageName)));
                    editor.putBoolean("dontshowagain", true);
                    editor.apply();
                    dialog.dismiss();
                })
                .setNeutralButton("Remind me later", (dialog, which) -> {
                    //Reset all details
                    editor.putLong("launch_count", 1);
                    long date_firstLaunch = System.currentTimeMillis();
                    editor.putLong("date_firstlaunch", date_firstLaunch);
                    editor.apply();
                    dialog.dismiss();
                })
                .setNegativeButton("No, thanks", (dialog, id) -> {
                    editor.putBoolean("dontshowagain", true);
                    editor.apply();
                    dialog.dismiss();
                });
        builder.create().show();
    }
}
