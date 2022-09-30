package com.example.shopmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shopmaster.adapters.UserButtonsAdapter;

public class UserActivity extends AppCompatActivity {

    private RecyclerView mRvButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mRvButtons = findViewById(R.id.rv_user_buttons);
        mRvButtons.setLayoutManager(new GridLayoutManager(UserActivity.this,2));
        mRvButtons.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(10, 50, 10, 50);
            }
        });
        mRvButtons.setAdapter(new UserButtonsAdapter(UserActivity.this, new UserButtonsAdapter.OnItemClickListener() {
            @Override
            public void onClick(String text) {
                Toast.makeText(UserActivity.this,text, Toast.LENGTH_SHORT).show();
            }
        }));

    }
}