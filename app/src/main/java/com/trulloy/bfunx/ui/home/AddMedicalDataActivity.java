package com.trulloy.bfunx.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.trulloy.bfunx.R;
import com.trulloy.bfunx.dbhelper.MedicalInfoDBHelper;

import java.util.Calendar;

public class AddMedicalDataActivity extends AppCompatActivity {

    private DatePickerDialog datePicker;
    private Context ctx;
    private MedicalInfoDBHelper mDbHelper;
    private Button saveBtn;
    private EditText dateEdtTxt, docNameEdtTxt, medicineEdtTxt, amountEdtText, reasonEdtTxt;
    private Spinner healTypeSpn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medical_data);
        mDbHelper = new MedicalInfoDBHelper(this);
        ctx = this;
        dateEdtTxt = findViewById(R.id.dateEdtTxt);
        docNameEdtTxt = findViewById(R.id.docnameEdtTxt);
        medicineEdtTxt = findViewById(R.id.medicineEdtTxt);
        amountEdtText = findViewById(R.id.amountEdtTxt);
        healTypeSpn = findViewById(R.id.healTypeSpn);
        reasonEdtTxt = findViewById(R.id.reasonEdtTxt);
        dateEdtTxt.setInputType(InputType.TYPE_NULL);
        dateEdtTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datePicker = new DatePickerDialog(ctx,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateEdtTxt.setText(dayOfMonth + " / " + (monthOfYear + 1) + " / " + year);
                            }
                        }, year, month, day);
                datePicker.show();
                datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#2B3B37"));
                datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#2B3B37"));
            }
        });

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
