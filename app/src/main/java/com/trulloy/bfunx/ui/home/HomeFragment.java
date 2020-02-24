package com.trulloy.bfunx.ui.home;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.trulloy.bfunx.AddMedicalInfoDBHelper;
import com.trulloy.bfunx.R;

public class HomeFragment extends Fragment {

    private AddMedicalInfoDBHelper mdb;
    private HomeViewModel homeViewModel;
    private TextView docVisitCountTxtView, lastVisitedTxtView, reasonTxtView, totalAmountTxtView, selfHealTxtView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mdb = new AddMedicalInfoDBHelper(getActivity());

        docVisitCountTxtView = root.findViewById(R.id.docVisitTxtView);
        lastVisitedTxtView = root.findViewById(R.id.lastVisitedTxtView);
        reasonTxtView = root.findViewById(R.id.reasonTxtView);
        totalAmountTxtView = root.findViewById(R.id.totalAmountTxtView);
        selfHealTxtView = root.findViewById(R.id.selfHealCountTxtView);
        //populateData();

        homeViewModel.docVisitCount.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer s) {
                docVisitCountTxtView.setText(s.toString());
            }
        });
        homeViewModel.lastDateVisited.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                lastVisitedTxtView.setText(s);
            }
        });
        homeViewModel.reason.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                reasonTxtView.setText(s);
            }
        });
        homeViewModel.totalAmount.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(@Nullable Double s) {
                totalAmountTxtView.setText(s.toString());
            }
        });
        homeViewModel.selfHealCount.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer s) {
                selfHealTxtView.setText(s);
            }
        });
        return root;
    }

    private void populateData() {
        Cursor data = mdb.getData();
        String lastVisit = "", reason = "";
        Integer docVisitCount = 0, selfHealCount = 0, temp;
        Double totalAmount = 0.0;

        while (data.moveToNext()) {
            lastVisit = data.getString(1);
            reason = data.getString(4);
            try {
                temp = Integer.parseInt(data.getString(3));
                totalAmount = totalAmount + Double.parseDouble(data.getString(6));
            } catch (NumberFormatException e) {
                temp = -1;
            }
            if (temp == 0)
                selfHealCount++;
            else
                docVisitCount++;
        }
        homeViewModel.setData(docVisitCount, lastVisit, reason, selfHealCount, totalAmount);
    }
}
