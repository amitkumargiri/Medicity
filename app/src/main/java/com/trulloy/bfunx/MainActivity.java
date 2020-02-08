package com.trulloy.bfunx;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView answerTxtView;
    ImageView answerImage;
    String answer;
    Button closeBtn, predict;
    DatePickerDialog datePicker;
    EditText dateEdtText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        closeBtn = findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               finish();
               System.exit(0);
           }
        });

        dateEdtText = findViewById(R.id.dateEdtTxt);
        dateEdtText.setInputType(InputType.TYPE_NULL);
        dateEdtText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datePicker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateEdtText.setText(dayOfMonth + " / " + (monthOfYear + 1) + " / " + year);
                            }
                        }, year, month, day);
                datePicker.show();
            }
        });

        predict = findViewById(R.id.predictBtn);
        answerTxtView = findViewById(R.id.answerTxtView);
        answerImage = findViewById(R.id.answerImg);
      
        predict.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PendulamRotation.prepareChart();
                EditText ageEdTxt = findViewById(R.id.ageEditTxt);
                int age;
                try {
                    age = Integer.parseInt(ageEdTxt.getText().toString());
                } catch (NumberFormatException e) {
                    age = 0;
                    // TODO - throw error and show dialog box
                }

                int month = -1;
                if (datePicker != null && datePicker.getDatePicker() != null) {
                    month = datePicker.getDatePicker().getMonth();
                }
                if (age < 18 || age > 45) {
                    answerTxtView.setTextColor(Color.RED);
                    answer = "Please input the age between 18 and 45.";
                    answerImage.setImageResource(R.drawable.sorryicon_100x85);
                } else if (month < 0) {
                    answerTxtView.setTextColor(Color.RED);
                    answer = "Please select date first.";
                    answerImage.setImageResource(R.drawable.sorryicon_100x85);
                } else {
                    answerTxtView.setTextColor(Color.rgb(0,150,0));
                    if (PendulamRotation.chart[month+1][age].intValue() == 0) {
                        answerImage.setImageResource(R.drawable.babyboy_100x108);
                        answer = "Your future baby will be BOY.";
                    } else if (PendulamRotation.chart[month+1][age].intValue() == 1) {
                        answerImage.setImageResource(R.drawable.babygirl_100x100);
                        answer = "Your future baby will be GIRL.";
                    }
                }
                answerTxtView.setText(answer);
            }
        });
    }
}
