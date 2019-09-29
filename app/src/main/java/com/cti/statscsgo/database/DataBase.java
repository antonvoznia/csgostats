package com.cti.statscsgo.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBase extends SQLiteOpenHelper {
    private String table_name;
    public DataBase(Context context) {
        super (context, "stats", null, 1);
    }
    public DataBase(Context context, String table_name) {
        super (context, "stats", null, 1);
        this.table_name = table_name;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("create table "+table_name+" (name text, value text);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    public void addNewTable(SQLiteDatabase db) {
        try {
            db.execSQL("create table " + table_name + " (name text, value text);");
        } catch (SQLiteException e) {
            Log.e("Error", "Can`t to create table.");
        }
    }

    //Return true if exists an table with the name.
    //In other case return false.
    public boolean isTableExist(SQLiteDatabase db, String name) {
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+name+"'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            } else {
                cursor.close();
                return false;
             }
        }
        cursor.close();
        return false;
    }

    public void deleteTable(SQLiteDatabase db, String name) {
        db.execSQL("DROP TABLE IF EXISTS "+name);
    }


}

