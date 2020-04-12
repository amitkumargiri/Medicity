package com.trulloy.bfunx.ui.vaccination;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.trulloy.bfunx.R;
import com.trulloy.bfunx.dbhelper.AddChildDetailsDBHelper;

public class AddChildActivity extends AppCompatActivity {

    private AddChildDetailsDBHelper mdb;
    private Button saveChildBtn;
    private EditText aadharEdtTxt, childNameEdtTxt, dobEdtTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        mdb = new AddChildDetailsDBHelper(this);
        saveChildBtn = findViewById(R.id.saveChildBtn);
        aadharEdtTxt = findViewById(R.id.aadharEdtTxt);
        childNameEdtTxt = findViewById(R.id.childNameEdtTxt);
        dobEdtTxt = findViewById(R.id.dobEdtTxt);
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
