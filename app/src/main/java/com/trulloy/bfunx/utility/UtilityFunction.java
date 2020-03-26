package com.trulloy.bfunx.utility;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UtilityFunction {

    public static HashMap<Integer, String> getColumnDetailsFromCursor(Cursor c) {
        HashMap<Integer, String> column = new HashMap<>();
        String[] columnList = c.getColumnNames();
        for (int i=0; i<columnList.length; i++) {
            column.put(c.getColumnIndex(columnList[i]), columnList[i]);
        }
        return column;
    }

    public static ArrayList<String> convertHasMapToArrayList(HashMap<Integer, String> h) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (Map.Entry<Integer, String> e : h.entrySet()) {
            arrayList.add(e.getKey() + ":" + e.getValue());
        }
        return arrayList;
    }
}
