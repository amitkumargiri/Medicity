package com.trulloy.bfunx.ui.vaccination;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.trulloy.bfunx.R;
import com.trulloy.bfunx.dbhelper.VaccineListDBHelper;

import java.util.ArrayList;

public class VaccinationListActivity extends AppCompatActivity {

    public static String childAadharNo;

    private VaccineListDBHelper mdb;
    private ListView vaccineListView;
    private Button saveVaccineFltBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination_list);

        mdb = new VaccineListDBHelper(this);
        vaccineListView = findViewById(R.id.vaccineList);
        saveVaccineFltBtn = findViewById(R.id.saveVaccineFltBtn);
        vaccineListView = findViewById(R.id.vaccineList);
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, populateData());
        vaccineListView.setAdapter(adapter);

    }

    private ArrayList<String> populateData() {
        Cursor data = mdb.getVaccineData(childAadharNo);
        ArrayList<String> vaccineList = new ArrayList<>();
        while (data.moveToNext()) {
            vaccineList.add(data.getString(0));
        }
        return vaccineList;
    }
}
