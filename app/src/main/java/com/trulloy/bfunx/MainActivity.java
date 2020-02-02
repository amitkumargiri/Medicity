package com.trulloy.bfunx;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView answerTxtView;
    ImageView answerImage;
    String answer;
    Button closeBtn, predict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner monthSpinner = findViewById(R.id.monthSpinner);
        ArrayAdapter<String> monthSpinnerAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.months));
        monthSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthSpinnerAdapter);
        closeBtn = findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               finish();
               System.exit(0);
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
                int month = (int) monthSpinner.getSelectedItemId();
                if (age < 18 || age > 45) {
                    month = -1;
                    age = 0;
                    answerTxtView.setTextColor(Color.RED);
                    answer = "Please input the age between 18 and 45.";
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
