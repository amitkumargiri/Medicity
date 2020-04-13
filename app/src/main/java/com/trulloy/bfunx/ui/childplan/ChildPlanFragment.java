package com.trulloy.bfunx.ui.childplan;

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

import com.trulloy.bfunx.R;
import com.trulloy.bfunx.utility.PendulamRotation;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass. This is for the child prediction.
 */
public class ChildPlanFragment extends Fragment {

    TextView answerTxtView;
    ImageView answerImage;
    String answer;
    Button closeBtn, predictBtn;
    DatePickerDialog datePicker;
    EditText dateEdtText;

    public ChildPlanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_child_plan, container, false);

        closeBtn = root.findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getActivity().finish();
                System.exit(0);
            }
        });

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

        return root;
    }

}
