package com.example.shopmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button mBtnJump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnJump=findViewById(R.id.btn_main_jump);

        mBtnJump.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,DatabaseTestActivity.class);
            startActivity(intent);
        });
    }
}