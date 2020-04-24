package com.trulloy.bfunx.ui.vaccination;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.trulloy.bfunx.R;
import com.trulloy.bfunx.dbhelper.VaccineListDBHelper;

import java.util.ArrayList;
import java.util.List;

public class VaccinationListActivity extends AppCompatActivity {

    protected static String childAadharNo;

    private VaccineListDBHelper mdb;
    private ListView vaccineListView;
    private Button saveVaccineBtn;
    private List<String> vaccines = VaccinationName.VACCINES_BIRTH;
    private List<String> vaccineListTakenByChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination_list);

        mdb = new VaccineListDBHelper(this);
        saveVaccineBtn = findViewById(R.id.saveVaccineBtn);
        vaccineListView = findViewById(R.id.birthVaccineList);
        vaccineListTakenByChild = populateData();
        vaccineListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        vaccineListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, vaccines){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View row = super.getView(position, convertView, parent);
                if(vaccineListTakenByChild.contains(getItem(position))) {
                    vaccineListView.setItemChecked(position,true);
                    row.setBackgroundColor (Color.GRAY); // some color // do something change color
                } else {
                    row.setBackgroundColor (Color.TRANSPARENT); // // default state and default color
                }
                return row;
            }
        });

        saveVaccineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checkedVaccines = vaccineListView.getCheckedItemPositions();
                for (int i=0; i<vaccines.size(); i++) {
                    if (!vaccineListTakenByChild.contains(vaccines.get(i)) && checkedVaccines.get(i)) {
                        mdb.insertData(childAadharNo, vaccines.get(i), "Today");
                    } else if (!checkedVaccines.get(i) && vaccineListTakenByChild.contains(vaccines.get(i)) ) {
                        mdb.deleteData(vaccines.get(i));
                    }
                }
                finish();
            }
        });
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