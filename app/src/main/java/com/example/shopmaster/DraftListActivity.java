package com.example.shopmaster;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.shopmaster.adapters.DraftListAdapter;
import com.example.shopmaster.datahandler.DBServer;
import com.example.shopmaster.datahandler.Grocery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DraftListActivity extends AppCompatActivity {
    private final String TAG = DraftListActivity.class.getSimpleName();
    RecyclerView mRv;
    TextView mTvTitle;
    Button mBtnSave, mBtnNext;
    List<Grocery> shopList;
    CardView cardView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft_list);

        mRv = findViewById(R.id.rv_draftlist);
        mTvTitle = findViewById(R.id.tv_draftlist_title);
        mBtnSave = findViewById(R.id.btn_draftlist_save);
        mBtnNext = findViewById(R.id.btn_draftlist_next);
        cardView = findViewById(R.id.cardview_draftlist);

        cardView.setBackgroundResource(R.drawable.bg_card_up_rounded);
        // cardView.setPreventCornerOverlap(true);


        DBServer db = new DBServer(this);
        shopList = db.findAllItemsInTable("NewListFragment");
        List<Object> storeShopList = organizeGroceriesByStore(shopList);

        DraftListAdapter adapter = new DraftListAdapter(this,storeShopList);
        mRv.setLayoutManager(new LinearLayoutManager(DraftListActivity.this));
        mRv.setAdapter(adapter);
        ((SimpleItemAnimator) mRv.getItemAnimator()).setSupportsChangeAnimations(false);

        mBtnSave.setOnClickListener(this::onClick);
        mBtnNext.setOnClickListener(this::onClick);

}
    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.btn_draftlist_save:
                Toast.makeText(this,
                        "Your shopping list has been saved.",Toast.LENGTH_SHORT).show();
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(DraftListActivity.this, TestMainActivity.class);
                        startActivity(intent);
                    }
                }, 800);

                break;
            case R.id.btn_draftlist_next:
                //TODO: Change it to Edit List. Test finalList for now.
                intent = new Intent(DraftListActivity.this,FinalListActivity.class);
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
