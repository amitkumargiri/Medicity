package com.trulloy.bfunx.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.trulloy.bfunx.dbhelper.Covid19AreaRatingDBHelper;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PushDataForRatingJobService extends JobService {

    private static String TAG = PushDataForRatingJobService.class.getName();
    private Context mContext = null;
    boolean isWorking = false;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean onStartJob(JobParameters params) {
        mContext = this;
        isWorking = true;
        new Thread(new Runnable() {
            public void run() {
                Covid19AreaRatingDBHelper mdb = new Covid19AreaRatingDBHelper(mContext);
                Cursor data = mdb.getAllDataAndClear();
                ArrayList<String> array = new ArrayList<>();
                while (data.moveToNext()) {
                    array.add(data.getString(0) + "," +
                            data.getString(1) + "," +
                            data.getString(2) + "," +
                            data.getString(3)
                    );
                }
            }
        }).start();
        return isWorking;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
