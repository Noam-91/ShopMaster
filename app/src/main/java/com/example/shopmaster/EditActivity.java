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

// EditList uses EditListAdapter to get updated search query results of items and store order

//all files involved:
// EditActivity
// activity_edit_list     : activity screen layout, holds nestedscrollview and nested recyclerviews
// layout_editlist_overall: parent recyclerview, holds the store SECTION and store information - store name, any additional distance, and the child recyclerview
// layout_editlist_store  : child recyclerview, holds the item layout and item information - item_name, item_image, item_price
// EditListItemFragment
// EditListAdapter

// EditListActivity and activity_edit_list are blank placeholders that allow the emulator app to run, without using the edit function



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
// set adapter and fragment per recyclerview - the recycler views shouldn't talk to each other, only to this activity

// modify the calculate() function's else{} code from PlanCalculator and use it here:
//        do the same findItemByName
//        sort all results into the stores they come from
//        then in increasing order of price

//        save that result into vars
//        pass it into the fragments/recyclerviews/adapters, so that:
//             parent recyclerview (store section) has store name, (optional) additional distance
//             child recyclerview (individual item) has item image, item name, item price

        }


    public void onClick(View view) {
//      will probably need to do fragment transaction when user clicks on an alternate item, to change screen back to draft list activity
//        if click item fragment,
//          replacing its draft list item with the new one
//          end this activity and frgment transact with the draft list fragment
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    }

}