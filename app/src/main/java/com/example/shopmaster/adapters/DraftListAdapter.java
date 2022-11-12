package com.example.shopmaster.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.shopmaster.R;
import com.example.shopmaster.datahandler.DBServer;
import com.example.shopmaster.datahandler.Grocery;
import com.example.shopmaster.dialog.DeleteConfirmDialog;
import com.example.shopmaster.network.DownloadImageTask;

import java.util.List;

public class DraftListAdapter extends RecyclerView.Adapter{
    private static final String TAG = DraftListAdapter.class.getName();
    private static final int VIEW_TYPE_TITLE = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private final Context mContext;
    private LayoutInflater mInflater;
    private List<Object> storeShopList;
    private final DBServer db;

    public DraftListAdapter(Context context, List<Object> storeShopList){
        this.mContext = context;
        this.storeShopList = storeShopList;
        db = new DBServer(mContext);

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
        int pos = holder.getAdapterPosition();
        if (storeShopList.get(pos) instanceof String){
            String storeName = (String) storeShopList.get(pos);
            ((TitleHolder)holder).tv_store.setText(storeName);
        }
        else{
            Grocery item = (Grocery) storeShopList.get(pos);
            ((ItemHolder)holder).tv_name.setText(item.getName());
            ((ItemHolder)holder).tv_price.setText("$"+item.getPrice());
            ((ItemHolder)holder).tv_cate.setText(item.getCate());
            Log.d(TAG,"Show items: quantity = "+item.getQuantity());
            ((ItemHolder)holder).tv_quantity.setText(String.valueOf(item.getQuantity()));
            RequestOptions reqOpt = RequestOptions
                    .fitCenterTransform()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(((ItemHolder)holder).iv_image.getWidth(),((ItemHolder)holder).iv_image.getHeight());
            Glide.with(mContext)
                    .load(item.getImgUrl())
                    .apply(reqOpt)
                    .into(((ItemHolder)holder).iv_image);

            ((ItemHolder)holder).btn_inc.setOnClickListener(view -> {
                int newQuantity = item.getQuantity()+1;
                item.setQuantity(newQuantity);
                ((Grocery) storeShopList.get(pos)).setQuantity(newQuantity);
                notifyItemChanged(pos);
                db.updateItemQuantity(item,"cart",newQuantity);
            });
            ((ItemHolder)holder).btn_dec.setOnClickListener(view -> {
                int newQuantity = item.getQuantity()-1;
                Log.d(TAG,"newQuantity: "+newQuantity);
                // quantity=0, delete item.
                if(newQuantity==0){
                    Log.d(TAG,"TRigger");
                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    alert.setTitle("Delete Item");
                    alert.setMessage("Are you sure you want to delete?");
                    alert.setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        db.deleteItem(item,"cart");
                        storeShopList.remove(item);
                        notifyItemRemoved(pos);
                    });
                    alert.setNegativeButton(android.R.string.no, (dialog, which) -> {
                        // close dialog
                        dialog.cancel();
                    });
                    alert.show();
                }else{
                    item.setQuantity(newQuantity);
                    Log.d(TAG,"Quantity decrement: "+item.getQuantity());
                    notifyItemChanged(pos);
                    db.updateItemQuantity(item,"cart",newQuantity);
                }

            });
            ((ItemHolder)holder).btn_alt.setOnClickListener(view -> {
                //TODO: Link to Edit List.
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
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
            tv_store= viewHolder.findViewById(R.id.tv_draftlist_store);
        }
    }

    public class ItemHolder extends ViewHolder{

        public ItemHolder(final View viewHolder) {
            super(viewHolder);
            tv_name = viewHolder.findViewById(R.id.tv_draftlist_name);
            tv_price= viewHolder.findViewById(R.id.tv_draftlist_price);
            tv_cate= viewHolder.findViewById(R.id.tv_draftlist_cate);
            tv_quantity= viewHolder.findViewById(R.id.tv_draftlist_quantity);
            iv_image = viewHolder.findViewById(R.id.iv_draftlist_image);
            btn_inc= viewHolder.findViewById(R.id.btn_draftlist_increment);
            btn_dec= viewHolder.findViewById(R.id.btn_draftlist_decrement);
            btn_alt= viewHolder.findViewById(R.id.btn_draftlist_alternative);
        }
    }

}
