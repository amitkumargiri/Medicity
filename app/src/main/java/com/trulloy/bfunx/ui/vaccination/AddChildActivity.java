package com.trulloy.bfunx.ui.vaccination;

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
import com.trulloy.bfunx.dbhelper.ChildDetailsDBHelper;

import java.util.Calendar;

public class AddChildActivity extends AppCompatActivity {

    private DatePickerDialog datePicker;
    private Context ctx;
    private ChildDetailsDBHelper mdb;
    private Button saveChildBtn;
    private EditText aadharEdtTxt, childNameEdtTxt, dobEdtTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        mdb = new ChildDetailsDBHelper(this);
        ctx = this;
        saveChildBtn = findViewById(R.id.saveChildBtn);
        aadharEdtTxt = findViewById(R.id.aadharEdtTxt);
        childNameEdtTxt = findViewById(R.id.childNameEdtTxt);
        dobEdtTxt = findViewById(R.id.dobEdtTxt);
        dobEdtTxt.setInputType(InputType.TYPE_NULL);
        dobEdtTxt.setOnClickListener(new View.OnClickListener() {
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
                                dobEdtTxt.setText(dayOfMonth + " / " + (monthOfYear + 1) + " / " + year);
                            }
                        }, year, month, day);
                datePicker.show();
                datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#2B3B37"));
                datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#2B3B37"));
            }
        });
        saveChildBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = mdb.insertData(aadharEdtTxt.getText().toString(), childNameEdtTxt.getText().toString(), dobEdtTxt.getText().toString());
                if (result) {
                    // TODO: Implement for the result
                }
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
