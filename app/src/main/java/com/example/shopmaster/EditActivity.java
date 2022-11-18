package com.example.shopmaster;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.widget.SearchView;
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
import com.example.shopmaster.adapters.ParentItemAdapter;
import com.example.shopmaster.datahandler.ChildItem;
import com.example.shopmaster.datahandler.DBServer;
import com.example.shopmaster.datahandler.Grocery;
import com.example.shopmaster.datahandler.ParentItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class EditActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private static final String KEY_ITEM_NAME = "item name";
    private static final String KEY_CART = "cart";
    private String itemName = null;
    private DBServer db;
    Grocery itemRemoving = new Grocery();

    SearchView searchView;
    ImageButton btnBack;
    RecyclerView parentRecyclerView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
        }
        itemName = getIntent().getStringExtra(KEY_ITEM_NAME);
        Log.d(TAG,"onCreate: get itemName: "+itemName);

        setContentView(R.layout.activity_edit);
        parentRecyclerView = findViewById(R.id.parent_recyclerview);
        btnBack = findViewById(R.id.btn_edit_back);
        searchView = findViewById(R.id.sv_edit);

        // DB
        db = new DBServer(this);

        // Extract itemName
        String keyword = null;
        String[] substrList =  itemName.split(" ");
        for (String substr: substrList){
            if (Grocery.getAllKeywords().contains(substr)){
                keyword = substr;
                break;
            }
        }
        Log.d(TAG,"Get keyword: "+keyword);

        // RecyclerView
        LinearLayoutManager layoutManager= new LinearLayoutManager(EditActivity.this);
        List<ParentItem> parentItemList =ParentItemList(keyword);
        ParentItemAdapter parentItemAdapter= new ParentItemAdapter(EditActivity.this,
                parentItemList,itemRemoving);
        parentRecyclerView.setAdapter(parentItemAdapter);
        parentRecyclerView.setLayoutManager(layoutManager);

        //searchView
        searchView.setQueryHint(keyword);

        }

    private List<ParentItem> ParentItemList(String keyword)
    {
        List<Grocery> relatedItems = db.findItemByName(keyword);
        List<ChildItem> targetList = new ArrayList<>();
        List<ChildItem> countyList = new ArrayList<>();
        List<ChildItem> walmartList = new ArrayList<>();
        List<ChildItem> costcoList = new ArrayList<>();
        for (Grocery item : relatedItems){
            ChildItem childItem = new ChildItem();
            if(item.getName().equals(itemName)){
                itemRemoving = item;
                continue;
            }
            switch (item.getStore()){
                case "Target":
                    childItem.setChildItemName(item.getName());
                    childItem.setChildItemPrice(item.getPrice());
                    childItem.setChildItemImgUrl(item.getImgUrl());
                    targetList.add(childItem);
                    break;
                case "County Market":
                    childItem.setChildItemName(item.getName());
                    childItem.setChildItemPrice(item.getPrice());
                    childItem.setChildItemImgUrl(item.getImgUrl());
                    countyList.add(childItem);
                    break;
                case "Walmart":
                    childItem.setChildItemName(item.getName());
                    childItem.setChildItemPrice(item.getPrice());
                    childItem.setChildItemImgUrl(item.getImgUrl());
                    walmartList.add(childItem);
                    break;
                case "Costco":
                    childItem.setChildItemName(item.getName());
                    childItem.setChildItemPrice(item.getPrice());
                    childItem.setChildItemImgUrl(item.getImgUrl());
                    costcoList.add(childItem);
                    break;
            }
        }

        List<ParentItem> itemList = new ArrayList<>();
        if (!targetList.isEmpty()){
            ParentItem parentItem= new ParentItem("Target",targetList);
            itemList.add(parentItem);
        }
        if (!countyList.isEmpty()){
            ParentItem parentItem= new ParentItem("County Market",countyList);
            itemList.add(parentItem);
        }
        if (!walmartList.isEmpty()){
            ParentItem parentItem= new ParentItem("Walmart",walmartList);
            itemList.add(parentItem);
        }
        if (!costcoList.isEmpty()){
            ParentItem parentItem= new ParentItem("Costco", costcoList);
            itemList.add(parentItem);
        }
        Log.d(TAG,"four store list length: "+targetList.size()+" "+countyList.size()+" "+walmartList.size()+" "+costcoList.size()+" ");
        Log.d(TAG,"first child list size: "+itemList.get(0).getChildItemList().size());
        return itemList;
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_edit_back:
                break;

        }

    }

    public void replaceItem(Grocery newItem){
        Grocery oldItem = new Grocery();
        oldItem.setName(itemName);
        db.deleteItem(oldItem,KEY_CART);
        db.addItem(newItem,KEY_CART);
    }

}