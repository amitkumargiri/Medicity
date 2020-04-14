package com.trulloy.bfunx.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MedicalInfoDBHelper extends SQLiteOpenHelper {

    private static final String TAG = "MedicalInfoDBHelper";

    private static final String TABLE_NAME = "VisitDetails";
    private static final String COL_SERIAL_NUMBER = "Srno";
    private static final String COL_VISITED_DATE = "VisitedDate";
    private static final String COL_DOCTOR_NAME = "DoctorName";
    private static final String COL_HEAL_TYPE = "HealType";
    private static final String COL_REASON = "Reason";
    private static final String COL_MEDICINE = "Medicine";
    private static final String COL_AMOUNT = "Amount";

    public MedicalInfoDBHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "create table IF NOT EXISTS " + TABLE_NAME + " (" +
                COL_SERIAL_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_VISITED_DATE + " TEXT," +
                COL_DOCTOR_NAME + " TEXT," +
                COL_HEAL_TYPE + " INTEGER," +
                COL_REASON + " TEXT," +
                COL_MEDICINE + " TEXT," +
                COL_AMOUNT + " REAL" + ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String visitedDate, String docName, int type, String reason, String medicine, double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_VISITED_DATE, visitedDate);
        contentValues.put(COL_DOCTOR_NAME, docName);
        contentValues.put(COL_HEAL_TYPE, type);
        contentValues.put(COL_REASON, reason);
        contentValues.put(COL_MEDICINE, medicine);
        contentValues.put(COL_AMOUNT, amount);

        Log.d(TAG, "InsertData: " + visitedDate + " " + docName + " " + type + " " + medicine + " " + amount);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "Srno = ?", new String[] {id});
    }
}