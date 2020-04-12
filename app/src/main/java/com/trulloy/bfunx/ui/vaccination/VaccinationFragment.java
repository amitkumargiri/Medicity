package com.trulloy.bfunx.ui.vaccination;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.trulloy.bfunx.R;
import com.trulloy.bfunx.dbhelper.AddChildDetailsDBHelper;

import java.util.ArrayList;

public class VaccinationFragment extends Fragment {

    private AddChildDetailsDBHelper mdb;
    private VaccinationViewModel vaccineViewModel;
    private ListView childListView;
    private Button addChildFltBtn;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        vaccineViewModel = ViewModelProviders.of(this).get(VaccinationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_vaccination, container, false);
        mdb = new AddChildDetailsDBHelper(getActivity());
        addChildFltBtn = root.findViewById(R.id.addChildFltBtn);
        addChildFltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addChild();
            }
        });
        childListView = root.findViewById(R.id.childList);
        ListAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, populateData());
        childListView.setAdapter(adapter);
        return root;
    }

    private void addChild() {
        Intent intent = new Intent(getActivity(), AddChildActivity.class);
        startActivityForResult(intent, 1);
    }

    private void openVaccineActivity() {
        Intent intent = new Intent(getActivity(), VaccinationListActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(1, Activity.RESULT_OK, data);
        ListAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, populateData());
        childListView.setAdapter(adapter);
    }

    private ArrayList<String> populateData() {
        Cursor data = mdb.getChildsList();
        ArrayList<String> childList = new ArrayList<>();
        while (data.moveToNext()) {
            childList.add(data.getString(0));
        }
        vaccineViewModel.setChildList(childList);
        return childList;
    }
}
