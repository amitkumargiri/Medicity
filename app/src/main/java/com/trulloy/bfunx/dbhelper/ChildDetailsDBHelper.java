package com.trulloy.bfunx.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChildDetailsDBHelper extends SQLiteOpenHelper {

    private static final String TAG = "ChildDetailsDBHelper";

    private static final String TABLE_NAME = "ChildDetails";
    private static final String COL_SERIAL_NUMBER = "Srno";
    private static final String COL_AADHAR_NO = "AadharNo";
    private static final String COL_CHILD_NAME = "Name";
    private static final String COL_DOB = "dob";

    public ChildDetailsDBHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "create table IF NOT EXISTS " + TABLE_NAME + " (" +
                COL_SERIAL_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_AADHAR_NO + " TEXT," +
                COL_CHILD_NAME + " TEXT," +
                COL_DOB + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String aadharno, String childName, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_AADHAR_NO, aadharno);
        contentValues.put(COL_CHILD_NAME, childName);
        contentValues.put(COL_DOB, dob);

        Log.d(TAG, "InsertData: " + aadharno + " " + childName + " " + dob);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }

    public Cursor getChildsList() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select " + COL_AADHAR_NO + ", " + COL_CHILD_NAME +" from " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public int deleteChild(String aadhar) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COL_AADHAR_NO + "=?", new String[]{aadhar});
    }
}
