package com.example.shopmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestMainActivity extends AppCompatActivity {
    private final String TAG = TestMainActivity.class.getSimpleName();
    private final String KEY_NEW_SHOPPING_LIST = "New Shopping List";
    private String [] testKeywordList = {"Banana","Beef","Bread"};


    private Button mBtnJump,mBtnOpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);

        mBtnJump=findViewById(R.id.btn_main_jump);
        mBtnOpt=findViewById(R.id.btn_main_optimize);

        mBtnJump.setOnClickListener(this::onClick);
        mBtnOpt.setOnClickListener(this::onClick);
    }

    private void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.btn_main_jump:
                intent = new Intent(TestMainActivity.this, TestDatabaseActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_main_optimize:
                intent = new Intent(TestMainActivity.this,OptimizeListActivity.class);
                List<String> testKeywordArray = new ArrayList<String>(Arrays.asList(testKeywordList));
                intent.putStringArrayListExtra(KEY_NEW_SHOPPING_LIST, (ArrayList<String>) testKeywordArray);
                startActivity(intent);
                break;

        }
    }
}