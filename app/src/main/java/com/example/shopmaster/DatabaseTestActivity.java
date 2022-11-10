package com.example.shopmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopmaster.datahandler.DBServer;
import com.example.shopmaster.datahandler.Grocery;
import com.example.shopmaster.network.DownloadImageTask;
import java.util.List;

public class DatabaseTestActivity extends AppCompatActivity {
    private final String TAG = this.getClass().toString();
    private TextView tv_id,tv_name,tv_cate,tv_price,tv_store,tv_quantity,tv_date;
    private ImageView iv_img;
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

        DBServer db = new DBServer(this);

        //show an item from grocery table.
        Grocery randomItem1 = db.findAllItemsInTable("grocery").get(9);
        displayItem(randomItem1);

        //add an item to shopping history.
        Grocery randomItem2 = db.findItemById(231,"-1","grocery");
        Log.d(TAG, "Found item from the grocery table.");
        randomItem2.setHistDate("20221001");
        db.addItem(randomItem2,"history");
        db.addItem(randomItem2,"history");
        db.addItem(randomItem2,"history");
        randomItem2 = db.findItemById(231,"20221001","history");
        displayItem(randomItem2);

        //find an item by partial name, add to cart, then edit the quantity.
        String[] stores = {"Target", "Costco","",""};   //You must add "" for placeholder.
        List<Grocery> itemCollection = db.findItemByNameAndStores("Bacon",stores);
        Grocery randomItem3 = itemCollection.get(1);
        db.addItem(randomItem3,"cart");
        db.addItem(itemCollection.get(0),"cart");
        db.updateItemQuantity(randomItem3,"cart", 99);
        randomItem3 = db.findItemById(randomItem3.getId(), "-1","cart");
        displayItem(randomItem3);

        //Find a list of item by category, and then delete one from current cart.
        itemCollection = db.findItemsByCategoryInTable("Meat & Seafood","cart");
        Grocery randomItem4 = itemCollection.get(0);
        db.deleteItem(randomItem4,"cart");
        itemCollection = db.findItemsByCategoryInTable("Meat & Seafood","cart");
        displayItem(itemCollection.get(0));
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
}

