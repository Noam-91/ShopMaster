package com.example.shopmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class CoverActivity extends AppCompatActivity {
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);

        // Display the Cover for 2.5 seconds and then move to the Main Page.
        mHandler.postDelayed(() -> {
            Intent intent = new Intent(CoverActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 2500);
    }

    public void onBackPressed() {

    }
}