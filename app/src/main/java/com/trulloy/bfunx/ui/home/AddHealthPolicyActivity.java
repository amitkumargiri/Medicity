package com.trulloy.bfunx.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.trulloy.bfunx.R;
import com.trulloy.bfunx.dbhelper.HealthPolicyDBHelper;

public class AddHealthPolicyActivity extends AppCompatActivity {

    private HealthPolicyDBHelper mdb;
    private Button saveHealthPolicy;
    private EditText policyNumberEdtTxt, policyNameEdtTxt, personNameEdtTxt, policyBuyDateEdtTxt, policyCoverageDetailEdtTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_health_policy);
        saveHealthPolicy = findViewById(R.id.saveHealthPolicy);
        mdb = new HealthPolicyDBHelper(this);
        policyNumberEdtTxt = findViewById(R.id.policyNumberEdtTxt);
        policyNameEdtTxt = findViewById(R.id.policyNameEdtTxt);
        personNameEdtTxt = findViewById(R.id.personNameEdtTxt);
        policyBuyDateEdtTxt = findViewById(R.id.policyBuyDateEdtTxt);
        policyCoverageDetailEdtTxt = findViewById(R.id.policyCoverageDetailEdtTxt);

        saveHealthPolicy = findViewById(R.id.saveHealthPolicy);
        saveHealthPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = mdb.insertData(policyNumberEdtTxt.getText().toString(), policyNameEdtTxt.getText().toString(),
                        personNameEdtTxt.getText().toString(), policyBuyDateEdtTxt.getText().toString(),
                        policyCoverageDetailEdtTxt.getText().toString());
                if (result) {

                }
            }
        });
    }
}