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
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopmaster.datahandler.DBServer;

public class SettingsActivity extends AppCompatActivity {
    Button btnBack, btnSubmitReport, btnSubmitFeedback;
    TextView tvUpdate, tvHelp, tvReport, tvFeedback,tvAppSettings,tvBigFont,tvDark,tvComm,tvReset,tvResetHint;
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
        tvAppSettings = findViewById(R.id.tv_settings_appsettings);
        tvBigFont = findViewById(R.id.tv_settings_bigfont);
        tvDark=findViewById(R.id.tv_settings_darkmode);
        tvComm=findViewById(R.id.tv_settings_communication);
        tvReset=findViewById(R.id.tv_settings_reset);
        tvResetHint=findViewById(R.id.tv_settings_resethint);
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

        //Big Font Mode
        switchBigFont.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    tvAppSettings.setTextSize(TypedValue.COMPLEX_UNIT_PX,tvAppSettings.getTextSize()+5);
                    tvBigFont.setTextSize(TypedValue.COMPLEX_UNIT_PX,tvBigFont.getTextSize()+5);
                    tvDark.setTextSize(TypedValue.COMPLEX_UNIT_PX,tvDark.getTextSize()+5);
                    tvUpdate.setTextSize(TypedValue.COMPLEX_UNIT_PX,tvUpdate.getTextSize()+5);
                    tvComm.setTextSize(TypedValue.COMPLEX_UNIT_PX,tvComm.getTextSize()+5);
                    tvHelp.setTextSize(TypedValue.COMPLEX_UNIT_PX,tvHelp.getTextSize()+5);
                    tvReport.setTextSize(TypedValue.COMPLEX_UNIT_PX,tvReport.getTextSize()+5);
                    tvFeedback.setTextSize(TypedValue.COMPLEX_UNIT_PX,tvFeedback.getTextSize()+5);
                    tvReset.setTextSize(TypedValue.COMPLEX_UNIT_PX,tvReset.getTextSize()+5);
                    tvResetHint.setTextSize(TypedValue.COMPLEX_UNIT_PX,tvResetHint.getTextSize()+5);
                    btnBack.setTextSize(TypedValue.COMPLEX_UNIT_PX,btnBack.getTextSize()+5);
                    btnSubmitFeedback.setTextSize(TypedValue.COMPLEX_UNIT_PX,btnSubmitFeedback.getTextSize()+5);
                    btnSubmitReport.setTextSize(TypedValue.COMPLEX_UNIT_PX,btnSubmitReport.getTextSize()+5);
                }else{
                    tvAppSettings.setTextSize(TypedValue.COMPLEX_UNIT_PX,tvAppSettings.getTextSize()-5);
                    tvBigFont.setTextSize(TypedValue.COMPLEX_UNIT_PX,tvBigFont.getTextSize()-5);
                    tvDark.setTextSize(TypedValue.COMPLEX_UNIT_PX,tvDark.getTextSize()-5);
                    tvUpdate.setTextSize(TypedValue.COMPLEX_UNIT_PX,tvUpdate.getTextSize()-5);
                    tvComm.setTextSize(TypedValue.COMPLEX_UNIT_PX,tvComm.getTextSize()-5);
                    tvHelp.setTextSize(TypedValue.COMPLEX_UNIT_PX,tvHelp.getTextSize()-5);
                    tvReport.setTextSize(TypedValue.COMPLEX_UNIT_PX,tvReport.getTextSize()-5);
                    tvFeedback.setTextSize(TypedValue.COMPLEX_UNIT_PX,tvFeedback.getTextSize()-5);
                    tvReset.setTextSize(TypedValue.COMPLEX_UNIT_PX,tvReset.getTextSize()-5);
                    tvResetHint.setTextSize(TypedValue.COMPLEX_UNIT_PX,tvResetHint.getTextSize()-5);
                    btnBack.setTextSize(TypedValue.COMPLEX_UNIT_PX,btnBack.getTextSize()-5);
                    btnSubmitFeedback.setTextSize(TypedValue.COMPLEX_UNIT_PX,btnSubmitFeedback.getTextSize()-5);
                    btnSubmitReport.setTextSize(TypedValue.COMPLEX_UNIT_PX,btnSubmitReport.getTextSize()-5);
                }
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