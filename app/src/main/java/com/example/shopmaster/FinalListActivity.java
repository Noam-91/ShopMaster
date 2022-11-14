package com.example.shopmaster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.shopmaster.adapters.FinalListAdapter;
import com.example.shopmaster.datahandler.DBServer;
import com.example.shopmaster.datahandler.Grocery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class FinalListActivity extends AppCompatActivity {
    private final String TAG = FinalListActivity.class.getSimpleName();
    private final String HISTORY = "history";
    private DBServer db;

    RecyclerView mRv;
    TextView mTvTitle;
    Button mBtnDone;
    List<Grocery> shopList;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_list);

        mRv = findViewById(R.id.rv_finallist);
        mTvTitle = findViewById(R.id.tv_finallist_title);
        mBtnDone = findViewById(R.id.btn_finallist_done);

        db = new DBServer(this);
        shopList = db.findAllItemsInTable("NewListFragment");
        List<Object> storeShopList = organizeGroceriesByStore(shopList);

        FinalListAdapter adapter = new FinalListAdapter(this,storeShopList);
        mRv.setLayoutManager(new LinearLayoutManager(FinalListActivity.this));
        mRv.setAdapter(adapter);
        ((SimpleItemAnimator) mRv.getItemAnimator()).setSupportsChangeAnimations(false);

        mBtnDone.setOnClickListener(this::onClick);
    }

    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.btn_finallist_done:
                //Save shopping history.
                Date date = new Date();
                for (Grocery item : shopList){
                    item.setHistDate(date.toString());
                    db.addItem(item,"history");
                }
                Toast.makeText(this, "Your shopping history on "+date+" has been saved",
                        Toast.LENGTH_SHORT).show();

                intent = new Intent(this, TestMainActivity.class);
                startActivity(intent);
                break;
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
