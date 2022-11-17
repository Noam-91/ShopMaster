package com.example.shopmaster;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.shopmaster.adapters.DraftListAdapter;
import com.example.shopmaster.adapters.EditListAdapter;
import com.example.shopmaster.adapters.FinalListAdapter;
import com.example.shopmaster.datahandler.DBServer;
import com.example.shopmaster.datahandler.Grocery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

// EditList uses DraftListAdapter to change/pass back the selected item into the list
// EditList uses EditListAdapter to get updated search query results of items and store order

public class EditActivity extends AppCompatActivity {
    SearchView mSv;
    NestedScrollView mNsv;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_edit);

        mSv = findViewById(R.id.edit_search);
        mNsv = findViewById(R.id.edit_content);

//        var: items that are already in the draft list

        // set adapter
//        shopList = db.findAllItemsInTable("NewListFragment");
//        List<Object> storeShopList = organizeGroceriesByStore(shopList);
//
//        FinalListAdapter adapter = new FinalListAdapter(this,storeShopList);
//        mRv.setLayoutManager(new LinearLayoutManager(FinalListActivity.this));
//        mRv.setAdapter(adapter);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


//        Fragment fragmentOne = new FragmentOne();
//        fragmentTransaction.add(R.id.edit_store_content, fragmentOne);
//        fragmentTransaction.addToBackStack(null);
//
//        fragmentTransaction.commit();
//
//        Fragment fragmentTwo = new FragmentTwo();
//        fragmentTransaction.replace(R.id.edit_store_contain, fragmentTwo);
//        fragmentTransaction.addToBackStack(null);
//
//        fragmentTransaction.commit();

    }

    public void onClick(View view) {
        Intent intent;

//        if click item fragment,
//          replacing its draft list item with the new one
//          end this activity

    }

    public void onSearch() {
//    compare and get items w closest strings as typed user input
//

    }

}