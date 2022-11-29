package com.example.shopmaster.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.shopmaster.R;
import com.example.shopmaster.datahandler.Grocery;

import java.util.List;

public class FinalListAdapter extends RecyclerView.Adapter{
    private static final String TAG = FinalListAdapter.class.getSimpleName();
    private static final int VIEW_TYPE_TITLE = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private final Context mContext;
    private final List<Object> storeShopList;

    public FinalListAdapter(Context context, List<Object> storeShopList) {
        this.mContext = context;
        this.storeShopList = storeShopList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG,"Final List View Holder activated.");
        RecyclerView.ViewHolder viewHolder=null;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        switch (viewType){
            case VIEW_TYPE_TITLE:
                viewHolder = new FinalListAdapter.TitleHolder(mInflater.inflate(R.layout.layout_finallist_categories, parent, false));
                break;
            case VIEW_TYPE_ITEM:
                viewHolder = new FinalListAdapter.ItemHolder(mInflater.inflate(R.layout.layout_finallist_items, parent, false));
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        if (storeShopList.get(pos) instanceof String){
            String storeName = (String) storeShopList.get(pos);
            ((FinalListAdapter.TitleHolder)holder).tv_store.setText(storeName);
            ((TitleHolder)holder).btn_map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri gmmIntentUri = Uri.parse("google.navigation:q="+storeName+",+Champaign+U.S.");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    mContext.startActivity(mapIntent);
                }
            });
        }
        else{
            Grocery item = (Grocery) storeShopList.get(pos);
            ((FinalListAdapter.ItemHolder)holder).tv_name.setText(item.getName());
            ((FinalListAdapter.ItemHolder)holder).tv_price.setText("$"+item.getPrice());
            ((FinalListAdapter.ItemHolder)holder).tv_cate.setText(item.getCate());
            Log.d(TAG,"Show items: quantity = "+item.getQuantity());
            ((FinalListAdapter.ItemHolder)holder).tv_quantity.setText(String.valueOf(item.getQuantity()));
            RequestOptions reqOpt = RequestOptions
                    .fitCenterTransform()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(((FinalListAdapter.ItemHolder)holder).iv_image.getWidth(),((FinalListAdapter.ItemHolder)holder).iv_image.getHeight());
            Glide.with(mContext)
                    .load(item.getImgUrl())
                    .apply(reqOpt)
                    .into(((FinalListAdapter.ItemHolder)holder).iv_image);
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_store, tv_name, tv_price,tv_cate,tv_quantity;
        public ImageView iv_image;
        public Button btn_done,btn_map;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
    public class TitleHolder extends FinalListAdapter.ViewHolder {

        public TitleHolder(View viewHolder) {
            super(viewHolder);
            tv_store= viewHolder.findViewById(R.id.tv_finallist_store);
            btn_map= viewHolder.findViewById(R.id.btn_finallist_map);
        }
    }

    public class ItemHolder extends FinalListAdapter.ViewHolder {

        public ItemHolder(final View viewHolder) {
            super(viewHolder);
            tv_name = viewHolder.findViewById(R.id.tv_finallist_name);
            tv_price= viewHolder.findViewById(R.id.tv_finallist_price);
            tv_cate= viewHolder.findViewById(R.id.tv_finallist_cate);
            tv_quantity= viewHolder.findViewById(R.id.tv_finallist_quantity);
            iv_image = viewHolder.findViewById(R.id.iv_finallist_image);
            btn_done= viewHolder.findViewById(R.id.btn_finallist_done);

        }
    }
}
