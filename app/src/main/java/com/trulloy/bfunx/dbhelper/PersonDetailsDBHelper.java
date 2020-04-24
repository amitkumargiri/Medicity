package com.trulloy.bfunx.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class PersonDetailsDBHelper extends SQLiteOpenHelper {

    private static final String TAG = "PersonDetailsDBHelper";

    private static final String TABLE_NAME = "PersonDetails";
    private static final String COL_SERIAL_NUMBER = "Srno";
    private static final String COL_EMAIL = "Email";
    private static final String COL_NAME = "Name";
    private static final String COL_PHONE = "Phone";
    private static final String COL_Country = "Country";
    private static final String COL_Pincode = "Pincode";
    private static final String COL_Locality = "Locality";

    public PersonDetailsDBHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "create table IF NOT EXISTS " + TABLE_NAME + " (" +
                COL_SERIAL_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_EMAIL + " TEXT," +
                COL_NAME + " TEXT," +
                COL_PHONE + " TEXT," +
                COL_Country + " TEXT," +
                COL_Pincode + " TEXT," +
                COL_Locality + " TEXT)";
        db.execSQL(createTable);
    }

    public boolean insertData(String email, String name, String phone, String country, String pincode, String locality) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (deleteData(email) == 0 ) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_EMAIL, email);
            contentValues.put(COL_NAME, name);
            contentValues.put(COL_PHONE, phone);
            contentValues.put(COL_Country, country);
            contentValues.put(COL_Pincode, pincode);
            contentValues.put(COL_Locality, locality);

            Log.d(TAG, "InsertData: " + email + " " + name + " " + phone + " " + country + " " + pincode + " " + locality);
            long result = db.insert(TABLE_NAME, null, contentValues);
            if (result == -1) {
                return false;
            }
        }
        return true;
    }

    public int deleteData(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COL_EMAIL + "=?", new String[]{email});
    }

    public Cursor getPersonInfoIfExist() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select " + COL_EMAIL + ", " +
                COL_NAME + ", " +
                COL_EMAIL + ", " +
                COL_PHONE + ", " +
                COL_Country + ", " +
                COL_Pincode + ", " +
                COL_Locality + " from " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}