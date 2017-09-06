package com.thelaundrychute.user.common.database;

import android.database.Cursor;
import android.database.CursorWrapper;



//TODO: Use for persisting app settings
public class AppCursorWrapper extends CursorWrapper {
    public AppCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public AppSettings getAppSettings() {
        String uuidString = getString(getColumnIndex(AppDbSchema.AppTable.Cols.UUID));
        String title = getString(getColumnIndex(AppDbSchema.AppTable.Cols.TITLE));
        long date = getLong(getColumnIndex(AppDbSchema.AppTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(AppDbSchema.AppTable.Cols.SOLVED));
        String status = getString(getColumnIndex(AppDbSchema.AppTable.Cols.STATUS));

        AppSettings appSettings = new AppSettings();
        //wash.setTitle(title);
       // wash.setDate(new Date(date));
        //wash.setSolved(isSolved != 0);
        //wash.setStatus(status);

        return appSettings;
    }
}
