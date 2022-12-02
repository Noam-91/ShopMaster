package com.example.shopmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopmaster.datahandler.DBServer;

public class SettingsActivity extends AppCompatActivity {
    Button btnBack, btnSubmitReport, btnSubmitFeedback;
    TextView tvUpdate, tvHelp, tvReport, tvFeedback;
    Switch switchBigFont,switchDarkMode;
    CardView cvReset;
    DBServer db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        btnBack = findViewById(R.id.btn_settings_back);
        switchBigFont = findViewById(R.id.switch_settings_bigfont);
        switchDarkMode = findViewById(R.id.switch_settings_darkmode);
        tvHelp = findViewById(R.id.tv_settings_help);
        tvReport = findViewById(R.id.tv_settings_report);
        tvUpdate = findViewById(R.id.tv_settings_update);
        tvFeedback = findViewById(R.id.tv_settings_feedback);
        cvReset = findViewById(R.id.cv_settings_reset);
        btnSubmitReport = findViewById(R.id.btn_report_submit);
        btnSubmitFeedback = findViewById(R.id.btn_feedback_submit);

        db = new DBServer(this);

        btnBack.setOnClickListener(this::onClick);
        tvHelp.setOnClickListener(this::onClick);
        tvReport.setOnClickListener(this::onClick);
        tvUpdate.setOnClickListener(this::onClick);
        tvFeedback.setOnClickListener(this::onClick);
        cvReset.setOnClickListener(this::onClick);
        btnSubmitReport.setOnClickListener(this::onClick);
        btnSubmitFeedback.setOnClickListener(this::onClick);

        // Dark Mode
        int currentMode = getResources().getConfiguration().uiMode &
                Configuration.UI_MODE_NIGHT_MASK;
        Log.d("Dark Mode", "currentMode: "+currentMode);
        switch(currentMode){
            case Configuration.UI_MODE_NIGHT_NO:
                switchDarkMode.setChecked(false);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                switchDarkMode.setChecked(true);
                break;
        }

        switchDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                Log.d("Dark Mode", "After switched, currentMode: "+(getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK));
                recreate();

            }
        });
    }

    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.btn_settings_back:
                intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.cv_settings_reset:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Reset App");
                alert.setMessage("Are you sure you want to reset the app?");
                alert.setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    db.clearNewList();
                    db.clearCart();
                    db.clearHistory();
                    Toast.makeText(this,"You have reset the app.",Toast.LENGTH_SHORT).show();
                });
                alert.setNegativeButton(android.R.string.no, (dialog, which) -> {
                    // close dialog
                    dialog.cancel();
                });
                alert.show();
                break;
            case R.id.btn_report_submit:
                Toast.makeText(this,"Thank you for reporting!",Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_feedback_submit:
                Toast.makeText(this,"Thank you for your feedback!",Toast.LENGTH_SHORT).show();
                break;


        }

    }
    @Override
    public void recreate() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        startActivity(getIntent());
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}