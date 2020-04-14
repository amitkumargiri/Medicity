package com.trulloy.bfunx.ui.allvisit;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.trulloy.bfunx.R;
import com.trulloy.bfunx.dbhelper.MedicalInfoDBHelper;

import java.util.ArrayList;

public class AllVisitsFragment extends Fragment {

    private MedicalInfoDBHelper mdb;
    private ListView mListView;
    private Button refreshBtn, deleteBtn;
    private EditText srnoEdtTxt;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View root = inflater.inflate(R.layout.fragment_allvisit, container, false);
        mListView = root.findViewById(R.id.listView);
        srnoEdtTxt = root.findViewById(R.id.srnoEdtTxt);
        refreshBtn = root.findViewById(R.id.refreshBtn);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateListView();
            }
        });
        deleteBtn = root.findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });
        mdb = new MedicalInfoDBHelper(getActivity());

        populateListView();
        return root;
    }

    public void populateListView() {
        Cursor data = mdb.getData();
        ArrayList<String> list = new ArrayList<>();
        while (data.moveToNext()) {
            list.add(
                data.getString(1) + "," +
                data.getString(2) + "," +
                data.getString(3) + "," +
                data.getString(4) + "," +
                data.getString(5) + "," +
                data.getString(6)
            );
        }
        ListAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list);
        mListView.setAdapter(adapter);
    }

    public void deleteData() {
        if (srnoEdtTxt.getText() != null) {
            Integer deleteRows = mdb.deleteData(srnoEdtTxt.getText().toString());
            if (deleteRows > 0) {
                Toast.makeText(this.getContext(), "Data deleted.", Toast.LENGTH_LONG);
            } else {
                Toast.makeText(this.getContext(), "Data NOT deleted.", Toast.LENGTH_LONG);
            }
        } else {
            Toast.makeText(this.getContext(), "Please enter serial number.", Toast.LENGTH_LONG);
        }
    }
}
