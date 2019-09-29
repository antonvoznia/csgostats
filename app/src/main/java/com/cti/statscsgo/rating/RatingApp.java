package com.cti.statscsgo.rating;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

public class RatingApp {

    private static void saveStatus(Context context, int count) {
        SharedPreferences shPre = context.getSharedPreferences("RATING", context.MODE_PRIVATE);
        SharedPreferences.Editor ed = shPre.edit();
        ed.putInt("COUNT", count);
        ed.commit();
    }

    private static int loadStatus(Context context) {
        SharedPreferences shPre = context.getSharedPreferences("RATING", context.MODE_PRIVATE);
        return shPre.getInt("COUNT", 7);
    }

    private static void setRating(final Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(true);
        dialog.setMessage("Rate this app?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=" + context.getApplicationContext().getPackageName());
                Intent rateAppIntent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(rateAppIntent);
                saveStatus(context, -1);
            }
        }).setNegativeButton("After", null).
                setNeutralButton("Never", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveStatus(context, -1);
                    }
                });
        dialog.show();
    }

    public static void showRatingDialog(Context context) {
        int count = loadStatus(context);
        if (count >= 10) {
            saveStatus(context, 0);
            setRating(context);
        } else if (count >= 0) {
            saveStatus(context, count+1);
        }
    }
}
