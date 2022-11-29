package com.example.shopmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopmaster.datahandler.DBServer;

public class SettingsActivity extends AppCompatActivity {
    Button btnBack;
    TextView tvBigFont, tvDarkLight, tvUpdate, tvHelp, tvReport, tvFeedback;
    CardView cvReset;
    DBServer db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        btnBack = findViewById(R.id.btn_settings_back);
        tvBigFont = findViewById(R.id.tv_settings_bigfontmode);
        tvDarkLight = findViewById(R.id.tv_settings_darkmode);
        tvHelp = findViewById(R.id.tv_settings_help);
        tvReport = findViewById(R.id.tv_settings_report);
        tvUpdate = findViewById(R.id.tv_settings_update);
        tvFeedback = findViewById(R.id.tv_settings_feedback);
        cvReset = findViewById(R.id.cv_settings_reset);

        db = new DBServer(this);

        btnBack.setOnClickListener(this::onClick);
        tvBigFont.setOnClickListener(this::onClick);
        tvDarkLight.setOnClickListener(this::onClick);
        tvHelp.setOnClickListener(this::onClick);
        tvReport.setOnClickListener(this::onClick);
        tvUpdate.setOnClickListener(this::onClick);
        tvFeedback.setOnClickListener(this::onClick);
        cvReset.setOnClickListener(this::onClick);
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
            case R.id.tv_settings_bigfontmode:
                break;

        }

    }
}