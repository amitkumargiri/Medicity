package com.trulloy.bfunx;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceFragmentCompat;

import com.trulloy.bfunx.dbhelper.PersonDetailsDBHelper;
import com.trulloy.bfunx.service.CovidTrackerJobService;

public class SettingsActivity extends AppCompatActivity {

    private Button editbtn, saveBtn, locationUpdateBtn;
    private EditText nameEdtTxt, emailEdtTxt, phoneEdtTxt, countryEdttxt, pincodeEdtTxt, localityEdtTxt;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                },
                2);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            AlertDialog alert = new AlertDialog.Builder(this).create();
            alert.setTitle("Error");
            alert.setMessage("Please provide the required permission to use Covid-19 features.");
            alert.show();
            onBackPressed();
        }

        nameEdtTxt = findViewById(R.id.settingNameEdtTxt);
        emailEdtTxt = findViewById(R.id.settingEmailEdtTxt);
        phoneEdtTxt = findViewById(R.id.settingPhoneEdtTxt);
        countryEdttxt = findViewById(R.id.settingCountryEdtTxt);
        pincodeEdtTxt = findViewById(R.id.settingPincodeEdtTxt);
        localityEdtTxt = findViewById(R.id.settingLocalityEdtTxt);

        locationUpdateBtn = findViewById(R.id.locationUpdateBtn);
        editbtn = findViewById(R.id.settingEditBtn);
        saveBtn = findViewById(R.id.settingSaveBtn);

        if (! updateUi()) {
            nameEdtTxt.setInputType(InputType.TYPE_CLASS_TEXT);
            emailEdtTxt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            phoneEdtTxt.setInputType(InputType.TYPE_CLASS_PHONE);
            locationUpdateBtn.setEnabled(true);
        }

        locationUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLocationViaGPS();
            }
        });

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameEdtTxt.setInputType(InputType.TYPE_CLASS_TEXT);
                emailEdtTxt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                phoneEdtTxt.setInputType(InputType.TYPE_CLASS_PHONE);
                locationUpdateBtn.setEnabled(true);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonDetailsDBHelper mdb = new PersonDetailsDBHelper(getParent());
                mdb.insertData(emailEdtTxt.getText().toString(), nameEdtTxt.getText().toString(), phoneEdtTxt.getText().toString()
                , countryEdttxt.getText().toString(), pincodeEdtTxt.getText().toString(), localityEdtTxt.getText().toString());
            }
        });
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

    public boolean updateUi() {
        PersonDetailsDBHelper mdb = new PersonDetailsDBHelper(this);
        Cursor data = mdb.getPersonInfoIfExist();
        if (data.moveToNext()) {
            nameEdtTxt.setText(data.getString(0));
            emailEdtTxt.setText(data.getString(1));
            phoneEdtTxt.setText(data.getString(2));
            countryEdttxt.setText(data.getString(3));
            pincodeEdtTxt.setText(data.getString(4));
            localityEdtTxt.setText(data.getString(5));
            locationUpdateBtn.setEnabled(false);
            saveBtn.setEnabled(false);
            return true;
        } else {
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void updateLocationViaGPS() {
        CovidTrackerJobService tracker = new CovidTrackerJobService();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tracker.getLocation();
            countryEdttxt.setText(tracker.getCountryName(this));
            pincodeEdtTxt.setText(tracker.getPostalCode(this));
            localityEdtTxt.setText(tracker.getLocality(this));
        } else {
            AlertDialog alert = new AlertDialog.Builder(this).create();
            alert.setTitle("Error");
            alert.setMessage("Please upgrade your phone to newer version.");
            alert.show();
            return;
        }
    }
}