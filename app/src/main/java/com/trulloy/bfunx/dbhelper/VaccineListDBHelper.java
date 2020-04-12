package com.trulloy.bfunx.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class VaccineListDBHelper extends SQLiteOpenHelper {

    private static final String TAG = "VaccineListDBHelper";

    private static final String TABLE_NAME = "VaccineList";
    private static final String COL_SERIAL_NUMBER = "Srno";
    private static final String COL_AADHAR_NO = "AadharNo";
    private static final String COL_VACCINE_NAME = "VaccineName";
    private static final String COL_YESNO = "YesNo";
    private static final String COL_DISTRIBUTED_DATE = "DistributeDate";
    private static final String COL_VACCINATED_DATE = "VaccineDate";

    public VaccineListDBHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "create table IF NOT EXISTS " + TABLE_NAME + " (" +
                COL_SERIAL_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_AADHAR_NO + " TEXT," +
                COL_VACCINE_NAME + " TEXT," +
                COL_YESNO + " TEXT," +
                COL_VACCINATED_DATE + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String aadharNo, String vaccineName, String yesno, String vaccinatedDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_AADHAR_NO, aadharNo);
        contentValues.put(COL_VACCINE_NAME, vaccineName);
        contentValues.put(COL_YESNO, yesno);
        contentValues.put(COL_VACCINATED_DATE, vaccinatedDate);

        Log.d(TAG, "InsertData: " + aadharNo + " " + vaccineName + " " + yesno + " " + vaccinatedDate);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }

    public Cursor getVaccineData(String childAadharNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select " + COL_VACCINE_NAME +" from " + TABLE_NAME + " where " + COL_AADHAR_NO + " = " + childAadharNo;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
