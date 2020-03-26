package com.trulloy.bfunx.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.trulloy.bfunx.R;
import com.trulloy.bfunx.dbhelper.AddMedicalInfoDBHelper;

public class AddMedicalDataActivity extends AppCompatActivity {

    private AddMedicalInfoDBHelper mDbHelper;
    private Button saveBtn;
    private EditText dateEdtTxt, docNameEdtTxt, medicineEdtTxt, amountEdtText, reasonEdtTxt;
    private Spinner healTypeSpn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medical_data);
        mDbHelper = new AddMedicalInfoDBHelper(this);

        dateEdtTxt = findViewById(R.id.dateEdtTxt);
        docNameEdtTxt = findViewById(R.id.docnameEdtTxt);
        medicineEdtTxt = findViewById(R.id.medicineEdtTxt);
        amountEdtText = findViewById(R.id.amountEdtTxt);
        healTypeSpn = findViewById(R.id.healTypeSpn);
        reasonEdtTxt = findViewById(R.id.reasonEdtTxt);

        saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    double amount = Double.parseDouble(amountEdtText.getText().toString());
                    boolean result = mDbHelper.insertData(dateEdtTxt.getText().toString(), docNameEdtTxt.getText().toString(),
                            healTypeSpn.getSelectedItemPosition(), reasonEdtTxt.getText().toString(), medicineEdtTxt.getText().toString(), amount);
                    if (result) {

                    }
                } catch (NumberFormatException e) {
                    // TODO: send the error message on screen
                } finally {
                    AddMedicalDataActivity.super.onBackPressed();
                }
            }
        });
    }
}
