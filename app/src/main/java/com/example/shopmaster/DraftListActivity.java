package com.example.shopmaster;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.shopmaster.adapters.DraftListAdapter;
import com.example.shopmaster.datahandler.DBServer;
import com.example.shopmaster.datahandler.Grocery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DraftListActivity extends AppCompatActivity {
    private final String TAG = this.getClass().toString();
    RecyclerView mRv;
    TextView mTvTitle;
    Button mBtnSave, mBtnNext;
    List<Grocery> shopList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft_list);

        mRv = findViewById(R.id.rv_draftlist);
        mTvTitle = findViewById(R.id.tv_draftlist_title);
        mBtnSave = findViewById(R.id.btn_draftlist_save);
        mBtnSave = findViewById(R.id.btn_draftlist_save);

        DBServer db = new DBServer(this);
        shopList = db.findAllItemsInTable("cart");
        List<Object> storeShopList = organizeGroceriesByStore(shopList);

        DraftListAdapter adapter = new DraftListAdapter(this,storeShopList);
        mRv.setLayoutManager(new LinearLayoutManager(DraftListActivity.this));
        mRv.setAdapter(adapter);
        ((SimpleItemAnimator) mRv.getItemAnimator()).setSupportsChangeAnimations(false);

//        mBtnSave.setOnClickListener(this::onClick);
//        mBtnNext.setOnClickListener(this::onClick);

}
    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.btn_draftlist_save:
                //TODO: Save draftlist.
            case R.id.btn_draftlist_next:
                //TODO: Next step.
        }
    }
    /**
     * Categorize the items by Store and return an object list for the recyclerView adapter.
     * @param shopList : shopList
     * @return : Sorted list with Stores name included.
     */
    public List<Object> organizeGroceriesByStore(List<Grocery> shopList){
        Log.d(TAG,"There are "+shopList.size()+" items to organize.");
        List<Object []> storeItemPairList = new ArrayList<>();
        Collections.sort(shopList, new Grocery.SortbyStoreCate());
        for (Grocery item : shopList){
            String store = item.getStore();
            Object[] storeItemPair ={store,item};
            storeItemPairList.add(storeItemPair);
        }
        List<Object> storeShopList = new ArrayList<>();

        for (Object[] pair : storeItemPairList){
            String store = (String)pair[0];
            Grocery item = (Grocery) pair[1];
            if(!storeShopList.contains(store)){
                storeShopList.add(store);
            }
            storeShopList.add(item);
        }

        Log.d(TAG,"There are "+storeShopList.size()+" items organized.");
        return storeShopList;
    }
}
