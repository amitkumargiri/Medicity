package com.trulloy.bfunx.ui.covid19;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trulloy.bfunx.R;
import com.trulloy.bfunx.dbhelper.PersonDetailsDBHelper;
import com.trulloy.bfunx.service.CovidTrackerJobService;
import com.trulloy.bfunx.utility.Constant;
import com.trulloy.bfunx.utility.JsonConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class Covid19Fragment extends Fragment {

    private Covid19Data worldData, countryData;
    private TextView worldConfirmedTxtView, worldRecoveredTxtView, worldDeceasedTxtView,
            countryConfirmedTxtView, countryRecoveredTxtView, countryDeceasedTxtView;

    private class JsonWorldTask extends AsyncTask<String, String, String> {
        
        protected void onPreExecute() {
            super.onPreExecute();
            // TODO: Show progress bar
        }

        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
                }

                JSONObject worldobj = new JSONObject(buffer.toString());
                JSONObject data = (JSONObject) worldobj.get(JsonConstant.WORLD);
                worldData = new Covid19Data(data.getInt(JsonConstant.CONFIRMED), data.getInt(JsonConstant.RECOVERED), data.getInt(JsonConstant.DECEASED));
                return "success";
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // TODO: Change the UI and update all state list
            if (worldData != null) {
                worldConfirmedTxtView.setText(worldData.getConfirmed());
                worldRecoveredTxtView.setText(worldData.getRecovered());
                worldDeceasedTxtView.setText(worldData.getDeceased());
            }
        }
    }

    private class JsonCountryTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            // TODO: Show progress bar
        }

        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
                }

                JSONObject worldobj = new JSONObject(buffer.toString());
                JSONObject data = (JSONObject) worldobj.get(JsonConstant.COUNTRY);
                countryData = new Covid19Data(data.getInt(JsonConstant.CONFIRMED), data.getInt(JsonConstant.RECOVERED), data.getInt(JsonConstant.DECEASED));
                return "success";
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // TODO: Change the UI and update all state list
            if (countryData != null) {
                countryConfirmedTxtView.setText(countryData.getConfirmed());
                countryRecoveredTxtView.setText(countryData.getRecovered());
                countryDeceasedTxtView.setText(countryData.getDeceased());
            }
        }
    }

    public Covid19Fragment() { }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_covid19, container, false);
        PersonDetailsDBHelper mdb = new PersonDetailsDBHelper(getActivity());
        Cursor data = mdb.getPersonInfoIfExist();
        if (!data.moveToNext()) {
            AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
            alert.setTitle("Error");
            alert.setMessage("Please provide the required permission to use Covid-19 features.");
            alert.show();
            getActivity().onBackPressed();
        }
        
        worldConfirmedTxtView = root.findViewById(R.id.worldConfirmedTxtView);
        worldRecoveredTxtView = root.findViewById(R.id.worldRecoveredTxtView);
        worldDeceasedTxtView = root.findViewById(R.id.worldDeceasedTxtView);
        countryConfirmedTxtView = root.findViewById(R.id.countryConfirmedTxtView);
        countryRecoveredTxtView = root.findViewById(R.id.countryRecoveredTxtView);
        countryDeceasedTxtView = root.findViewById(R.id.countryDeceasedTxtView);
        
        ActivityCompat.requestPermissions(this.getActivity(),
                new String[]{
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                },
                2);

        new JsonWorldTask().execute(Constant.API_URL_FOR_WORLD_DATA);
        new JsonCountryTask().execute(Constant.API_URL_FOR_WORLD_DATA);

        JobScheduler jobScheduler = (JobScheduler) getContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo jobInfo = new JobInfo.Builder(Constant.JOB_SCHEDULER_TRACKER_ID, new ComponentName(getContext(), CovidTrackerJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(Constant.T_15_MINS)
                .build();
        jobScheduler.schedule(jobInfo);
        return root;
    }
}