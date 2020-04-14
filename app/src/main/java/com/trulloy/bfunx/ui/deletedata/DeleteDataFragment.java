package com.trulloy.bfunx.ui.deletedata;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.trulloy.bfunx.R;
import com.trulloy.bfunx.dbhelper.AddChildDetailsDBHelper;
import com.trulloy.bfunx.dbhelper.HealthPolicyDBHelper;
import com.trulloy.bfunx.dbhelper.VaccineListDBHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteDataFragment extends Fragment {

    private TextView displayTxtView;
    private Spinner dataTypeSpn;
    private EditText dataEdtTxt;
    private Button deleteBtn;

    public DeleteDataFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_delete_data, container, false);
        dataTypeSpn = root.findViewById(R.id.dataTypeSpn);
        displayTxtView = root.findViewById(R.id.displayTxtView);
        dataEdtTxt = root.findViewById(R.id.dataEdtTxt);
        deleteBtn = root.findViewById(R.id.deleteBtn);

        dataTypeSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    displayTxtView.setText("Policy Number");
                } else {
                    displayTxtView.setText("Aadhar Number"); // 1 means child vaccination data
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                displayTxtView.setText("Policy Number");
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equalsIgnoreCase(dataEdtTxt.getText().toString())) {
                    //TODO: Implement alert dialog
                } else {
                    if (0 == dataTypeSpn.getSelectedItemPosition()) {
                        HealthPolicyDBHelper db = new HealthPolicyDBHelper(getContext());
                        db.deletePolicy(dataEdtTxt.getText().toString());
                    } else {
                        // 1 means child vaccination data
                        String aadhar = dataEdtTxt.getText().toString();
                        AddChildDetailsDBHelper db = new AddChildDetailsDBHelper(getContext());
                        db.deleteChild(aadhar);
                        VaccineListDBHelper vdb = new VaccineListDBHelper(getContext());
                        vdb.deleteDataByAdhar(aadhar);
                    }
                    //TODO: Implement success dialog box
                    dataEdtTxt.setText("");
                }
            }
        });
        return root;
    }
}
