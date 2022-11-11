package com.example.shopmaster.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmaster.R;
import com.example.shopmaster.datahandler.DBServer;
import com.example.shopmaster.datahandler.Grocery;
import com.example.shopmaster.network.DownloadImageTask;

import java.util.List;

public class DraftListAdapter extends RecyclerView.Adapter{
    private static final String TAG = DraftListAdapter.class.getName();
    private static final int VIEW_TYPE_TITLE = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Object> storeShopList;
    private DBServer db;

    public DraftListAdapter(Context context, List<Object> storeShopList){
        this.mContext = context;
        this.storeShopList = storeShopList;
        DBServer db = new DBServer(mContext);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG,"Draft List View Holder activated.");
        RecyclerView.ViewHolder viewHolder=null;
        mInflater = LayoutInflater.from(mContext);
        switch (viewType){
            case VIEW_TYPE_TITLE:
                viewHolder = new TitleHolder(mInflater.inflate(R.layout.layout_draftlist_categories, parent, false));
                break;
            case VIEW_TYPE_ITEM:
                viewHolder = new ItemHolder(mInflater.inflate(R.layout.layout_draftlist_items, parent, false));
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (storeShopList.get(position) instanceof String){
            String storeName = (String) storeShopList.get(position);
            ((TitleHolder)holder).tv_store.setText(storeName);
        }
        else{
            Grocery item = (Grocery) storeShopList.get(position);
            ((ItemHolder)holder).tv_name.setText(item.getName());
            ((ItemHolder)holder).tv_price.setText("$"+item.getPrice());
            ((ItemHolder)holder).tv_cate.setText(item.getCate());
            Log.d(TAG,"Show items: quantity = "+item.getQuantity());
            ((ItemHolder)holder).tv_quantity.setText(String.valueOf(item.getQuantity()));

            new DownloadImageTask(((ItemHolder)holder).iv_image).execute(item.getImgUrl());
            ((ItemHolder)holder).btn_inc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int newQuantity = item.getQuantity()+1;
                    item.setQuantity(newQuantity);
                    ((ItemHolder)holder).tv_quantity.setText(String.valueOf(newQuantity));
                    db.updateItemQuantity(item,"cart",newQuantity);
                }
            });
            ((ItemHolder)holder).btn_dec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int newQuantity = item.getQuantity()-1;
                    item.setQuantity(newQuantity);
                    ((ItemHolder)holder).tv_quantity.setText(String.valueOf(newQuantity));
                    db.updateItemQuantity(item,"cart",newQuantity);
                    //TODO: quantity==0, delete dialog.
                }
            });
            ((ItemHolder)holder).btn_alt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: Link to Edit List.
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG,"getItemCount: "+storeShopList.size());
        return storeShopList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (storeShopList.get(position) instanceof String){
            return VIEW_TYPE_TITLE;
        }
        else{
            return VIEW_TYPE_ITEM;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_store, tv_name, tv_price,tv_cate,tv_quantity;
        public ImageView iv_image;
        public Button btn_inc, btn_dec, btn_alt;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
    public class TitleHolder extends ViewHolder {

        public TitleHolder(View viewHolder) {
            super(viewHolder);
            tv_store= (TextView) viewHolder.findViewById(R.id.tv_draftlist_store);
        }
    }

    public class ItemHolder extends ViewHolder{

        public ItemHolder(final View viewHolder) {
            super(viewHolder);
            tv_name =(TextView)viewHolder.findViewById(R.id.tv_draftlist_name);
            tv_price=(TextView)viewHolder.findViewById(R.id.tv_draftlist_price);
            tv_cate=(TextView)viewHolder.findViewById(R.id.tv_draftlist_cate);
            tv_quantity=(TextView)viewHolder.findViewById(R.id.tv_draftlist_quantity);
            iv_image =(ImageView) viewHolder.findViewById(R.id.iv_draftlist_image);
            btn_inc=(Button)viewHolder.findViewById(R.id.btn_draftlist_increment);
            btn_dec=(Button)viewHolder.findViewById(R.id.btn_draftlist_decrement);
            btn_alt=(Button)viewHolder.findViewById(R.id.btn_draftlist_alternative);
        }
    }

}
