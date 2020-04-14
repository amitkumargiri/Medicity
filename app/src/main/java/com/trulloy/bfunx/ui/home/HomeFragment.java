package com.trulloy.bfunx.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.trulloy.bfunx.dbhelper.MedicalInfoDBHelper;
import com.trulloy.bfunx.R;

public class HomeFragment extends Fragment {

    private MedicalInfoDBHelper mdb;
    private HomeViewModel homeViewModel;
    private Button healthPoliciesBtn;
    private TextView docVisitCountTxtView, lastVisitedTxtView, reasonTxtView, totalAmountTxtView, selfHealTxtView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mdb = new MedicalInfoDBHelper(getActivity());

        healthPoliciesBtn = root.findViewById(R.id.healthPoliciesBtn);
        healthPoliciesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHealthPolicyActivity();
            }
        });

        docVisitCountTxtView = root.findViewById(R.id.docVisitTxtView);
        lastVisitedTxtView = root.findViewById(R.id.lastVisitedTxtView);
        reasonTxtView = root.findViewById(R.id.reasonTxtView);
        totalAmountTxtView = root.findViewById(R.id.totalAmountTxtView);
        selfHealTxtView = root.findViewById(R.id.selfHealCountTxtView);
        populateData();
        homeViewModel.docVisitCount.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer s) {
                if (s != null) docVisitCountTxtView.setText(s.toString());
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
                if (s != null) totalAmountTxtView.setText(s.toString());
            }
        });
        homeViewModel.selfHealCount.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer s) {
                if (s != null) selfHealTxtView.setText(s.toString());
            }
        });
        return root;
    }

    private void populateData() {
        Cursor data = mdb.getData();
        String lastVisit = "", reason = "";
        Integer docVisitCount = 0, selfHealCount = 0, temp;
        Double totalAmount = 0.0, tempAmt = 0.0;

        while (data.moveToNext()) {
            tempAmt = 0.0;
            try {
                temp = Integer.parseInt(data.getString(3));
                tempAmt = data.getDouble(6);
            } catch (NumberFormatException e) {
                temp = -1;
            }
            totalAmount = totalAmount + tempAmt;
            if (temp == 0)
                selfHealCount++;
            else {
                docVisitCount++;
                lastVisit = data.getString(1);
                reason = data.getString(4);
            }
        }
        homeViewModel.setData(docVisitCount, lastVisit, reason, selfHealCount, totalAmount);
    }

    private void openHealthPolicyActivity() {
        Intent intent = new Intent(getActivity(), HealthPolicyActivity.class);
        this.startActivity(intent);
    }
}
