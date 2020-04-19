package com.trulloy.bfunx.ui.covid19;


import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.trulloy.bfunx.R;
import com.trulloy.bfunx.dbhelper.Covid19AreaRatingDBHelper;
import com.trulloy.bfunx.service.CovidTracker;
import com.trulloy.bfunx.service.CovidTrackerJobService;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Covid19Fragment extends Fragment {

    private List<String> locList = new ArrayList<>();
    private ListView locListView;
    private Button deleteLocationBtn;
    public Covid19Fragment() { }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_covid19, container, false);
        locListView = root.findViewById(R.id.locListView);
        deleteLocationBtn = root.findViewById(R.id.deleteLocationBtn);
        ActivityCompat.requestPermissions(this.getActivity(),
                new String[]{
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                },
                2);
        final Covid19AreaRatingDBHelper mdb = new Covid19AreaRatingDBHelper(getActivity());
        Cursor data = mdb.getAllData();
        while (data.moveToNext()) {
            locList.add(data.getString(0) + ", " + data.getString(1) + ":" + data.getString(2));
        }
        JobScheduler jobScheduler = (JobScheduler) getContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo jobInfo = new JobInfo.Builder(101, new ComponentName(getContext(), CovidTrackerJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(1000 * 60 * 15)
                .build();
        jobScheduler.schedule(jobInfo);
        ListAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, locList);
        locListView.setAdapter(adapter);
        deleteLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdb.deleteData();
//                Intent intent = new Intent(getContext(), CovidTracker.class);
//                getActivity().startService(intent);
            }
        });
        return root;
    }
}
