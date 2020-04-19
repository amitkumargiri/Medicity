package com.trulloy.bfunx.ui.childplan;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.trulloy.bfunx.R;
import com.trulloy.bfunx.utility.PendulamRotation;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass. This is for the child prediction.
 */
public class ChildPlanFragment extends Fragment {

    private TextView answerTxtView;
    private ImageView answerImage;
    private String answer;
    private Button predictBtn;
    private DatePickerDialog datePicker;
    private EditText dateEdtText;
    private AdView mAdView;

    public ChildPlanFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_child_plan, container, false);

        dateEdtText = root.findViewById(R.id.dateEdtTxt);
        dateEdtText.setInputType(InputType.TYPE_NULL);
        dateEdtText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datePicker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateEdtText.setText(dayOfMonth + " / " + (monthOfYear + 1) + " / " + year);
                            }
                        }, year, month, day);
                datePicker.show();
                datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#2B3B37"));
                datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#2B3B37"));
            }
        });

        predictBtn = root.findViewById(R.id.predictBtn);
        answerTxtView = root.findViewById(R.id.answerTxtView);
        answerImage = root.findViewById(R.id.answerImg);

        predictBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PendulamRotation.prepareChart();
                EditText ageEdTxt = getActivity().findViewById(R.id.ageEditTxt);
                int age;
                try {
                    age = Integer.parseInt(ageEdTxt.getText().toString());
                } catch (NumberFormatException e) {
                    age = 0;
                    AlertDialog alert = new AlertDialog.Builder(getContext()).create();
                    alert.setTitle("Error:");
                    alert.setMessage("You have entered wrong age. Closing this application forcefully.");
                    alert.show();
                    getActivity().onBackPressed();
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
        mAdView = root.findViewById(R.id.bannerAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        AlertDialog alert = new AlertDialog.Builder(getContext()).create();
        alert.setTitle("Notice:");
        alert.setMessage("This is only for fun and entertainment only. Please enter the month on which you had sex and required female details only.");
        alert.show();
        return root;
    }
}
