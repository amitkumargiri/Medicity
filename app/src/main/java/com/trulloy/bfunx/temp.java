/*
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


    <?xml version="1.0" encoding="utf-8"?>

<android.widget.LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingLeft="16dp"
    android:paddingRight="16dp"
    android:orientation="vertical"
    android:gravity="top">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="75dp"
        android:orientation="horizontal"
        android:layout_margin="10dp">

        <ImageView
            android:layout_width="62dp"
            android:layout_height="match_parent"
            android:src="@mipmap/ic_launcher" />

        <TextView
            android:layout_width="190dp"
            android:layout_height="match_parent"
            android:gravity="center"
            android:paddingTop="20px"
            android:text="Female Details Only."
            android:textColor="@android:color/black"
            android:textStyle="italic"
            android:textAlignment="center" />

        <Button
            android:id="@+id/closeBtn"
            android:layout_width="70px"
            android:layout_height="70px"
            android:background="@drawable/close_72x72" />

    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:orientation="horizontal"
        android:layout_margin="10dp">
        <TextView
            android:layout_width="100dp"
            android:layout_height="match_parent"
            android:gravity="center"
            android:text="Age"
            android:textAlignment="center" />

        <EditText
            android:id="@+id/ageEditTxt"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:ems="10"
            android:gravity="center"
            android:inputType="number"
            android:text="18" />
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:orientation="horizontal"
        android:layout_margin="10dp">
        <TextView
            android:layout_width="100dp"
            android:layout_height="match_parent"
            android:gravity="center"
            android:text="Sex Month"
            android:textAlignment="center" />

        <EditText
            android:id="@+id/dateEdtTxt"
            android:layout_width="250dp"
            android:layout_height="wrap_content"
            android:ems="10"
            android:hint="Enter Date"
            android:layout_marginRight="10dp"/>

    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:layout_margin="10dp">

        <Button
            android:id="@+id/predictBtn"
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:text="Predict"
            android:textStyle="bold"
            android:background="@android:color/holo_green_light"
            android:typeface="serif"/>
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:orientation="horizontal"
        android:textAlignment="center"
        android:layout_margin="10dp">

        <TextView
            android:id="@+id/answerTxtView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:gravity="center"
            android:text="Future baby..."
            android:textColor="@android:color/black"
            android:textStyle="bold"/>
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="100dp"
        android:orientation="horizontal"
        android:textAlignment="center"
        android:layout_margin="10dp"
        android:gravity="center">

        <ImageView
            android:id="@+id/answerImg"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:background="@android:color/white"/>
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:orientation="horizontal"
        android:textAlignment="center"
        android:gravity="center"
        android:layout_margin="10dp">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="This application is only for fun and entertainment only. Please enter the month on which you had sex in 'Sex Month'." />
    </LinearLayout>
</android.widget.LinearLayout>
 */

