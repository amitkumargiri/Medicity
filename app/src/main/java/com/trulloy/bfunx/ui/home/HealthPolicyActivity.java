package com.trulloy.bfunx.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.trulloy.bfunx.R;
import com.trulloy.bfunx.dbhelper.HealthPolicyDBHelper;

import java.util.ArrayList;

public class HealthPolicyActivity extends AppCompatActivity {

    private HealthPolicyDBHelper mdb;
    private ListView healthPoliciesList;
    private Button addPolicyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_policy);
        addPolicyBtn = findViewById(R.id.addPolicyBtn);
        addPolicyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAddHealthPolicyActivity();
            }
        });

        healthPoliciesList = findViewById(R.id.healthPoliciesList);
        mdb = new HealthPolicyDBHelper(this);
        Cursor data = mdb.getPolicyNumberListData();
        ArrayList<String> list = new ArrayList<>();

        while (data.moveToNext()) {
            list.add(data.getString(0));
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        healthPoliciesList.setAdapter(adapter);

        healthPoliciesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HealthPolicyDetailsActivity.HEALTH_POLICY_NUMBER = (String) healthPoliciesList.getItemAtPosition(position);
                openHealthPolicyDetailsActivity();
            }
        });
    }

    private void startAddHealthPolicyActivity() {
        Intent intent = new Intent(this, AddHealthPolicyActivity.class);
        startActivity(intent);
    }

    private void openHealthPolicyDetailsActivity() {
        Intent intent = new Intent(this, HealthPolicyDetailsActivity.class);
        startActivity(intent);
    }
}
