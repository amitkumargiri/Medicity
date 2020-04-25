package com.trulloy.bfunx.dbhelper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class Covid19AreaRatingDBHelper extends SQLiteOpenHelper {

    private static final String TAG = "Covid19AreaRating";

    private static final String TABLE_NAME = "AreaRating";
    private static final String COL_TIME = "Time";
    private static final String COL_PINCODE = "Pincode";
    private static final String COL_LOCATION_LAT = "Lat";
    private static final String COL_LOCATION_LONG = "Long";

    public Covid19AreaRatingDBHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "create table IF NOT EXISTS " + TABLE_NAME + " (" +
                COL_TIME + " TEXT, " +
                COL_PINCODE + " TEXT, " +
                COL_LOCATION_LAT + " REAL," +
                COL_LOCATION_LONG + " REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String time, String pincode, double latitude, double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TIME, time);
        contentValues.put(COL_PINCODE, pincode);
        contentValues.put(COL_LOCATION_LAT, latitude);
        contentValues.put(COL_LOCATION_LONG, longitude);

        Log.d(TAG, "InsertData: " + latitude + " " + longitude);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }

    public Cursor getAllDataAndClear() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "Select * from " + TABLE_NAME;
        Cursor data = db.rawQuery(selectQuery, null);
        db.rawQuery("DELETE from " + TABLE_NAME, null);
        return data;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public int deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE from " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data.getCount();
    }
}