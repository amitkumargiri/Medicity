package com.trulloy.bfunx.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.trulloy.bfunx.dbhelper.Covid19AreaRatingDBHelper;
import com.trulloy.bfunx.utility.Constant;
import com.trulloy.bfunx.utility.UtilityFunction;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.InputStream;
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

                /** Send data to Server **/
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), Constant.T_10_SECONDS); //Timeout Limit
                HttpResponse response;
                JSONObject json = new JSONObject();

                try {
                    HttpPost post = new HttpPost(Constant.URL_TO_PUSH_DATA_FOR_RATING);
                    json.put("email", "test@xyz.com");
                    json.put("data", array);
                    StringEntity se = new StringEntity( json.toString());
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);
                    response = client.execute(post);

                    /* Checking response */
                    if(response != null){
                        //InputStream in = response.getEntity().getContent(); //Get the data in the entity
                    }
                } catch(Exception e) {
                    e.printStackTrace();
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
