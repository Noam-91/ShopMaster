package com.example.shopmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.shopmaster.datahandler.DBServer;
import com.example.shopmaster.datahandler.Grocery;
import com.example.shopmaster.datahandler.PlanCalculator;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OptimizeListActivity extends AppCompatActivity {
    private final String TAG = OptimizeListActivity.class.getSimpleName();
    private final String KEY_NEW_SHOPPING_LIST = "New Shopping List";
    private List<String> keywordList = new ArrayList<>();
    private String primaryFactor = "time";
    private int numOfStops = 1;
    DBServer db;

    private TabLayout mTabLayout;
    private NumberPicker mNp;
    private Button mBtnNext, mBtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        keywordList = getIntent().getStringArrayListExtra(KEY_NEW_SHOPPING_LIST);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimize_list);

        db = new DBServer(this);

        mTabLayout = findViewById(R.id.tabLayout_optimizelist);
        mNp = findViewById(R.id.np_optimizelist);
        mBtnNext = findViewById(R.id.btn_optimizelist_next);
        mBtnBack = findViewById(R.id.btn_optimizelist_back);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        Toast.makeText(OptimizeListActivity.this, "Save time", Toast.LENGTH_SHORT).show();
                        primaryFactor="time";
                        Log.d(TAG,"Primary Factor: "+primaryFactor);
                        break;
                    case 1:
                        primaryFactor="money";
                        Log.d(TAG,"Primary Factor: "+primaryFactor);
                        Toast.makeText(OptimizeListActivity.this, "Save money", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (mNp!=null){
            mNp.setMinValue(1);
            mNp.setMaxValue(4);
            mNp.setWrapSelectorWheel(false);
            mNp.isVerticalFadingEdgeEnabled();
            mNp.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    numOfStops=newVal;
                    Log.d(TAG,"Number of stops: "+newVal);
                }
            });

        }

        mBtnBack.setOnClickListener(this::onClick);
        mBtnNext.setOnClickListener(this::onClick);
    }

    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.btn_optimizelist_next:
//                Log.d(TAG,"key word list: "+keywordList.toString());
//                PlanCalculator calculator = new PlanCalculator(this,keywordList,primaryFactor,numOfStops);
//                List<Grocery> shopList = calculator.calculate();
//                try {
//                    db.clearCart();
//                    db.addList(shopList,"cart");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                intent = new Intent(OptimizeListActivity.this,DraftListActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_optimizelist_back:
                //TODO: Back to new shopping list activity.
                intent = new Intent(OptimizeListActivity.this, TestMainActivity.class);
                intent.putStringArrayListExtra(KEY_NEW_SHOPPING_LIST, (ArrayList<String>) keywordList);
                startActivity(intent);
                break;


        }
    }
}