package com.example.shopmaster.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.shopmaster.R;
import com.example.shopmaster.datahandler.Grocery;

import java.util.List;

// EditList uses DraftListAdapter to change/pass back the selected item into the list
// EditList uses EditListAdapter to get updated search query results of items and store order

//    adapts parent recycler view - the order of stores / general store display
//    adapts child recycler view - the items specifically on display & their order of display


public class EditListAdapter extends RecyclerView.Adapter {

    private static final String TAG = EditListAdapter.class.getSimpleName();
//    TAG in EditListAdapter?
    private static final int VIEW_TYPE_TITLE = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private final Context mContext;
    private List<Object> storeShopList;


    //     Initialize the dataset of the Adapter.
    public EditListAdapter(Context context, List<Object> storeShopList) {
        this.mContext = context;
        this.storeShopList = storeShopList;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG,"Final List View Holder activated.");
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater mInflater = LayoutInflater.from(mContext);

        // Create a new view, which defines the UI of the list item
        switch (viewType){
            case VIEW_TYPE_TITLE:
                viewHolder = new EditListAdapter.TitleHolder(mInflater.inflate(
                                R.layout.layout_editlist_overall, parent, false));
                break;
            case VIEW_TYPE_ITEM:
                viewHolder = new EditListAdapter.ItemHolder(mInflater.inflate(
                                R.layout.layout_editlist_store, parent, false));
        }

        return (EditListAdapter.ViewHolder) viewHolder;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
//        viewHolder.getTextView().setText(localDataSet[position]);

//        search for the item in the database
//        sort store results in ascending distance
//              distance seems hardcoded to be Target -> County Market rn
//              display the parent recyclerview
//        sort item results in ascending price
//              display the child recyclerview

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return storeShopList.size();
    }


//  actual recyclerview content
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView store_name, store_dist, item_name, item_price;
        public SearchView searchview;
        public ImageView item_img;
        public HorizontalScrollView store_section_container;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class TitleHolder extends EditListAdapter.ViewHolder {
        public TitleHolder(View viewHolder) {
            super(viewHolder);
            store_name = viewHolder.findViewById(R.id.edit_store_name);
            store_dist = viewHolder.findViewById(R.id.edit_store_dist);
        }
    }
    public class ItemHolder extends EditListAdapter.ViewHolder {
        public ItemHolder(final View viewHolder) {
            super(viewHolder);
            searchview = viewHolder.findViewById(R.id.edit_search);
            item_img = viewHolder.findViewById(R.id.edit_store_item_img);
            item_name = viewHolder.findViewById(R.id.edit_store_item_name);
            item_price = viewHolder.findViewById(R.id.edit_store_item_price);
        }
    }
}