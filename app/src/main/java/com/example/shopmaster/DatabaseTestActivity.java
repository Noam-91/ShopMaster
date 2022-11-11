package com.example.shopmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopmaster.datahandler.DBServer;
import com.example.shopmaster.datahandler.Grocery;
import com.example.shopmaster.datahandler.PlanCalculator;
import com.example.shopmaster.network.DownloadImageTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseTestActivity extends AppCompatActivity {
    private final String TAG = this.getClass().toString();
    private TextView tv_id,tv_name,tv_cate,tv_price,tv_store,tv_quantity,tv_date;
    private ImageView iv_img;
    private Button btn_test_draftlist;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_test);

        tv_id = findViewById(R.id.tv_test_id);
        tv_name = findViewById(R.id.tv_test_name);
        tv_cate = findViewById(R.id.tv_test_cate);
        tv_price = findViewById(R.id.tv_test_price);
        tv_store = findViewById(R.id.tv_test_store);
        tv_quantity = findViewById(R.id.tv_test_quantity);
        tv_date = findViewById(R.id.tv_test_historyDate);
        iv_img = findViewById(R.id.iv_test_image);
        btn_test_draftlist = findViewById(R.id.btn_test_draftlist);

        btn_test_draftlist.setOnClickListener(this::onClick);

        DBServer db = new DBServer(this);

//        //show an item from grocery table.
//        Grocery randomItem1 = db.findAllItemsInTable("grocery").get(9);
//        displayItem(randomItem1);
//
//        //add an item to shopping history.
//        Grocery randomItem2 = db.findItemById(231,"-1","grocery");
//        Log.d(TAG, "Found item from the grocery table.");
//        randomItem2.setHistDate("20221001");
//        db.addItem(randomItem2,"history");
//        db.addItem(randomItem2,"history");
//        db.addItem(randomItem2,"history");
//        randomItem2 = db.findItemById(231,"20221001","history");
//        displayItem(randomItem2);
//
//        //find an item by partial name and stores, add to cart, then edit the quantity.
//        String[] stores = {"Target", "Costco","",""};   //You must add "" for placeholder.
//        List<Grocery> itemCollection = db.findItemByNameAndStores("Bacon",stores);
//        Grocery randomItem3 = itemCollection.get(1);
//        db.addItem(randomItem3,"cart");
//        db.addItem(itemCollection.get(0),"cart");
//        db.updateItemQuantity(randomItem3,"cart", 99);
//        randomItem3 = db.findItemById(randomItem3.getId(), "-1","cart");
//        displayItem(randomItem3);
//
//        //Find a list of item by category, and then delete one from current cart.
//        itemCollection = db.findItemsByCategoryInTable("Meat & Seafood","cart");
//        Grocery randomItem4 = itemCollection.get(0);
//        db.deleteItem(randomItem4,"cart");
//        itemCollection = db.findItemsByCategoryInTable("Meat & Seafood","cart");
//        displayItem(itemCollection.get(0));
//
//        // PlanCalculator Test
//        List<String> shoplist = Arrays.asList("Banana", "Beef", "Bread");
//        PlanCalculator planCalculator = new PlanCalculator(this,shoplist,"time",2);
//        itemCollection = planCalculator.calculate();
//        for (Grocery item : itemCollection){
//            Log.d(TAG,"PlanCalculator Test: "+item.toString());
//          }

        // Add random items.
        db.clearCart();
        Grocery randomItem = db.findItemById(1,"-1","grocery");
        randomItem.setQuantity(5);
        db.addItem(randomItem,"cart");
        randomItem = db.findItemById(20,"-1","grocery");
        randomItem.setQuantity(5);
        db.addItem(randomItem,"cart");
        randomItem = db.findItemById(120,"-1","grocery");
        randomItem.setQuantity(5);
        db.addItem(randomItem,"cart");
        randomItem = db.findItemById(70,"-1","grocery");
        randomItem.setQuantity(5);
        db.addItem(randomItem,"cart");
        randomItem = db.findItemById(220,"-1","grocery");
        randomItem.setQuantity(5);
        db.addItem(randomItem,"cart");
        randomItem = db.findItemById(240,"-1","grocery");
        randomItem.setQuantity(5);
        db.addItem(randomItem,"cart");
        randomItem = db.findItemById(290,"-1","grocery");
        randomItem.setQuantity(5);
        db.addItem(randomItem,"cart");
        randomItem = db.findItemById(320,"-1","grocery");
        randomItem.setQuantity(5);
        db.addItem(randomItem,"cart");
        randomItem = db.findItemById(350,"-1","grocery");
        randomItem.setQuantity(5);
        db.addItem(randomItem,"cart");
        }


    /**
     * Set values to display the item.
     * For items in "cart" and "grocery", the variable "date" is always -1.
     * @param item: item
     */
    private void displayItem(Grocery item){
        tv_id.setText("Item_id: "+item.getId());
        tv_name.setText("Item_name: "+item.getName());
        tv_cate.setText("Category: "+item.getCate());
        tv_price.setText("Price: $"+item.getPrice());
        tv_store.setText("Store: "+item.getStore());
        tv_quantity.setText("Quantity: "+item.getQuantity());
        tv_date.setText("History Date: "+item.getHistDate());
        new DownloadImageTask(iv_img).execute(item.getImgUrl());
    }

    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.btn_test_draftlist:
                intent = new Intent(this,DraftListActivity.class);
                startActivity(intent);
                break;

        }

    }
}

