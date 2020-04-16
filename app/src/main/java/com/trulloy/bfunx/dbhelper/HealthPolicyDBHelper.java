package com.trulloy.bfunx.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HealthPolicyDBHelper extends SQLiteOpenHelper {

    private static final String TAG = "HealthPolicyDBHelper";

    private static final String TABLE_NAME = "HealthPolicies";
    private static final String COL_SERIAL_NUMBER = "Srno";
    private static final String COL_POLICY_NUMBER = "PolicyNumber";
    private static final String COL_POLICY_NAME = "PolicyName";
    private static final String COL_POLICY_AMOUNT = "PolicyAmount";
    private static final String COL_PERSON_NAME = "PersonName";
    private static final String COL_POLICY_BUY_DATE = "PolicyBuyDate";
    private static final String COL_POLICY_RENEWED_DATE = "PolicyRenewedDate";
    private static final String COL_POLICY_NEXT_RENEWED_DATE = "PolicyNextRenewedDate";
    private static final String COL_POLICY_COVERAGE = "PolicyCoverage";

    public HealthPolicyDBHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "create table IF NOT EXISTS " + TABLE_NAME + " (" +
                COL_SERIAL_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_POLICY_NUMBER + " TEXT," +
                COL_POLICY_NAME + " TEXT," +
                COL_PERSON_NAME + " TEXT," +
                COL_POLICY_AMOUNT + " REAL," +
                COL_POLICY_BUY_DATE + " TEXT," +
                COL_POLICY_RENEWED_DATE + " TEXT," +
                COL_POLICY_NEXT_RENEWED_DATE + " TEXT," +
                COL_POLICY_COVERAGE + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String policyNumber, String policyName, double policyAmount, String personName, String buyDate, String coverage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_POLICY_NUMBER, policyNumber);
        contentValues.put(COL_POLICY_NAME, policyName);
        contentValues.put(COL_POLICY_AMOUNT, policyAmount);
        contentValues.put(COL_PERSON_NAME, personName);
        contentValues.put(COL_POLICY_BUY_DATE, buyDate);
        contentValues.put(COL_POLICY_COVERAGE, coverage);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }

    public Cursor getPolicyNumberListData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select " + COL_POLICY_NUMBER +  " from " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getPolicyData(String policyNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select " +
                COL_POLICY_NUMBER   + ", " +
                COL_POLICY_NAME     + ", " +
                COL_POLICY_AMOUNT   + ", " +
                COL_PERSON_NAME     + ", " +
                COL_POLICY_BUY_DATE + ", " +
                COL_POLICY_COVERAGE +
                " from " + TABLE_NAME + " where " +
                COL_POLICY_NUMBER + " ='"+ policyNumber +"'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public int deletePolicy(String policyNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COL_POLICY_NUMBER + "=?", new String[]{policyNumber});
    }
}
