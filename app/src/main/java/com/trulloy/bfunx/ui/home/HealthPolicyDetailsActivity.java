package com.trulloy.bfunx.ui.home;


import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.trulloy.bfunx.R;
import com.trulloy.bfunx.dbhelper.HealthPolicyDBHelper;

public class HealthPolicyDetailsActivity extends AppCompatActivity {

    protected static String HEALTH_POLICY_NUMBER;
    private HealthPolicyDBHelper mdb;
    private HealthPolicyModel hpModel;
    private Button closeBtn;
    private TextView policyNumberTxt, policyNameTxt, policyAmoutTxt, personNameTxt,
            policyBuyDateTxt, policyCoverageTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_policy_details);

        closeBtn = findViewById(R.id.closeBtn);
        policyNumberTxt = findViewById(R.id.policyNumberTxt);
        policyNameTxt = findViewById(R.id.policyNameTxt);
        policyAmoutTxt = findViewById(R.id.policyAmountTxt);
        personNameTxt = findViewById(R.id.personNameTxt);
        policyBuyDateTxt = findViewById(R.id.policyBuyDateTxt);
        policyCoverageTxt = findViewById(R.id.policyCoverageTxt);

        if (HEALTH_POLICY_NUMBER != null) {
            mdb = new HealthPolicyDBHelper(this);
            Cursor data = mdb.getPolicyData(HEALTH_POLICY_NUMBER);
            while (data.moveToNext()) {
                hpModel = new HealthPolicyModel(
                            data.getString(0), // policy number
                            data.getString(1), // policy name
                            data.getString(2), // policy amount
                            data.getString(3), // person name
                            data.getString(4), // buy date
                            data.getString(5)  // coverage
                          );
            }
            policyNumberTxt.setText(hpModel.getNumber());
            policyNameTxt.setText(hpModel.getPolicyName());
            policyAmoutTxt.setText(hpModel.getPolicyAmount());
            personNameTxt.setText(hpModel.getPersonName());
            policyBuyDateTxt.setText(hpModel.getBuyDate());
            policyCoverageTxt.setText(hpModel.getCoverage());
        }

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
