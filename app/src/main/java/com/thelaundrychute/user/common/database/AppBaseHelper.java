package com.thelaundrychute.user.common.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class AppBaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "AppBaseHelper";
    private static final int VERSION = 2;
    private static final String DATABASE_NAME = "appBase.db";

    public AppBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + AppDbSchema.AppTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                        AppDbSchema.AppTable.Cols.UUID + ", " +
                        AppDbSchema.AppTable.Cols.TITLE + ", " +
                        AppDbSchema.AppTable.Cols.DATE + ", " +
                        AppDbSchema.AppTable.Cols.STATUS + ", " +
                        AppDbSchema.AppTable.Cols.SOLVED +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
