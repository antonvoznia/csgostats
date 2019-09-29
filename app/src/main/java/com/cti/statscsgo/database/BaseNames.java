package com.cti.statscsgo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseNames extends SQLiteOpenHelper {
    public BaseNames(Context context) {
        super (context, "names", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table names_steam (name text, count number);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    public boolean deleteRow(SQLiteDatabase db, String name) {
        String whereClause = "name = ?";
        String[] whereArgs = new String[] { name };
        db.delete("names_steam", whereClause, whereArgs);
        //return db.delete("names_steam", "name" + "=" + name, null) > 0;
        return true;
    }

}

