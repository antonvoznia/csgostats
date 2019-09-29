package com.cti.statscsgo.update;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.cti.statscsgo.StatsActivity;

public class Update  {

    public Update() { }

    public String loadUpdate(Context ctx) {
        SharedPreferences shPre; shPre = ctx.getSharedPreferences("UPDATE", ctx.MODE_PRIVATE);
        return shPre.getString("UPDATE", "0");
    }

    public void startUpdate(Context ctx) {
        SharedPreferences shPre; shPre = ctx.getSharedPreferences("UPDATE", ctx.MODE_PRIVATE);
        SharedPreferences.Editor ed = shPre.edit();
        ed.putString("UPDATE", "1");
        ed.commit();
    }

    public void stopUpdate(Context ctx) {
        SharedPreferences shPre; shPre = ctx.getSharedPreferences("UPDATE", ctx.MODE_PRIVATE);
        SharedPreferences.Editor ed = shPre.edit();
        ed.putString("UPDATE", "0");
        ed.commit();
    }
}
