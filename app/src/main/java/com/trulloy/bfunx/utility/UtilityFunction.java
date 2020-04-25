package com.trulloy.bfunx.utility;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
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


    public static boolean isNetworkConnected(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean result = cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
        if (result) {
            try {
                InetAddress ipAddr = InetAddress.getByName("google.com");
                // !ipAddr.equals("");
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public static boolean createLock(Context ctx, String activity) {
        PackageManager m = ctx.getPackageManager();
        try {
            PackageInfo p = m.getPackageInfo(ctx.getPackageName(), 0);
            File file = new File(activity + ".lock", p.applicationInfo.dataDir);
            if (!file.exists()) {
                return file.createNewFile();
            }
        } catch (PackageManager.NameNotFoundException | IOException e) {

        }
        return false;
    }

    public static boolean releaseLock(Context ctx, String activity) {
        PackageManager m = ctx.getPackageManager();
        try {
            PackageInfo p = m.getPackageInfo(ctx.getPackageName(), 0);
            File file = new File(activity + ".lock", p.applicationInfo.dataDir);
            if (file.exists()) { return file.delete(); }
        } catch (PackageManager.NameNotFoundException e) {

        }
        return false;
    }
}
