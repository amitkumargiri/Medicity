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

import com.trulloy.bfunx.R;
import com.trulloy.bfunx.dbhelper.HealthPolicyDBHelper;

import java.util.Calendar;

public class AddHealthPolicyActivity extends AppCompatActivity {

    private DatePickerDialog datePicker;
    private Context ctx;
    private HealthPolicyDBHelper mdb;
    private Button saveHealthPolicy;
    private EditText policyNumberEdtTxt, policyNameEdtTxt, policyAmountEdtTxt, personNameEdtTxt, policyBuyDateEdtTxt, policyCoverageDetailEdtTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_health_policy);
        saveHealthPolicy = findViewById(R.id.saveHealthPolicy);
        mdb = new HealthPolicyDBHelper(this);
        ctx = this;
        policyNumberEdtTxt = findViewById(R.id.policyNumberEdtTxt);
        policyNameEdtTxt = findViewById(R.id.policyNameEdtTxt);
        policyAmountEdtTxt = findViewById(R.id.policyAmountEdtTxt);
        personNameEdtTxt = findViewById(R.id.personNameEdtTxt);
        policyBuyDateEdtTxt = findViewById(R.id.policyBuyDateEdtTxt);
        policyCoverageDetailEdtTxt = findViewById(R.id.policyCoverageDetailEdtTxt);
        policyBuyDateEdtTxt.setInputType(InputType.TYPE_NULL);
        policyBuyDateEdtTxt.setOnClickListener(new View.OnClickListener() {
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
                                policyBuyDateEdtTxt.setText(dayOfMonth + " / " + (monthOfYear + 1) + " / " + year);
                            }
                        }, year, month, day);
                datePicker.show();
                datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#2B3B37"));
                datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#2B3B37"));
            }
        });
        saveHealthPolicy = findViewById(R.id.saveHealthPolicy);
        saveHealthPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ("".equalsIgnoreCase(policyNumberEdtTxt.getText().toString())
                        || "".equalsIgnoreCase(policyAmountEdtTxt.getText().toString())) {
                    //TODO: Implement a dialog box or Some better UI
                } else {
                    try {
                        Double amount = Double.parseDouble(policyAmountEdtTxt.getText().toString());
                        boolean result = mdb.insertData(policyNumberEdtTxt.getText().toString(), policyNameEdtTxt.getText().toString(),
                                amount, personNameEdtTxt.getText().toString(), policyBuyDateEdtTxt.getText().toString(),
                                policyCoverageDetailEdtTxt.getText().toString());
                        if (result) {
                        }
                        finish();
                    } catch (NumberFormatException e) {
                        //TODO: Implement a dialog box
                    }
                }
            }
        });
    }
}