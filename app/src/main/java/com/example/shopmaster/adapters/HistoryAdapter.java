package com.example.shopmaster.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopmaster.R;
import com.example.shopmaster.datahandler.DBServer;
import com.example.shopmaster.datahandler.Grocery;
import com.example.shopmaster.fragments.HistoryFragment;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final Context histContext;

    List<String> ids;
    List<String> titles;
    List<String> cates;
    List<String> prices;
    List<String> stores;
    List<String> images;
    List<String> qty;
    List<String> dates;
    LayoutInflater inflater;

    private OnHistCardListener onHistCardListener;

    public HistoryAdapter(Context ctx,
                          List<List<String>> itemList,
                          OnHistCardListener onHistCardListener)
    {

        this.ids = itemList.get(0);
        this.titles = itemList.get(1);
        this.cates = itemList.get(2);
        this.prices = itemList.get(3);
        this.stores = itemList.get(4);
        this.images = itemList.get(5);
        this.qty = itemList.get(6);
        this.dates = itemList.get(7);

        histContext = ctx;
        this.inflater = LayoutInflater.from(ctx);
        this.onHistCardListener = onHistCardListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.history_card, parent, false);
        return new ViewHolder(view, onHistCardListener );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(titles.get(position));
        holder.price.setText("$ "+prices.get(position));
        holder.store.setText(stores.get(position));

        holder.id = Integer.parseInt(ids.get(position));
        holder.cate = cates.get(position);
        holder.qty = Integer.parseInt(qty.get(position));
        holder.date = dates.get(position);
        holder.imgurl = images.get(position);

//        holder.image.setImageResource(images.get(position));
        Glide.with(histContext).load(images.get(position)).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        TextView title;
        TextView price;
        TextView store;
        ImageView image;

        Integer id;
        String cate;
        Integer qty;
        String date;
        String imgurl;

        OnHistCardListener onHistCardListener;

        public ViewHolder(@NonNull View itemView, OnHistCardListener onHistCardListener) {
            super(itemView);
            title = itemView.findViewById(R.id.buy_again_item_name);
            price = itemView.findViewById(R.id.buy_again_item_price);
            image = itemView.findViewById(R.id.buy_again_image);
            store = itemView.findViewById(R.id.hist_store_name);

            this.onHistCardListener = onHistCardListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            PopupMenu hist_popup = new PopupMenu(view.getContext(), view);
            hist_popup.inflate(R.menu.history_card_popup);
            hist_popup.setOnMenuItemClickListener(this);
            hist_popup.show();
            onHistCardListener.onHistCardClick(getAdapterPosition(), view);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch(menuItem.getItemId()){
                case R.id.action_add:
                    DBServer db = new DBServer(histContext);
                    Grocery cartItem = db.findItemById(id, "-1", "grocery");
                    cartItem.setQuantity(1);
                    db.addItem(cartItem, "newList");
                    Toast.makeText(histContext,cartItem.getName()+" has been " +
                            "added to current shopping list.",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_cancel:
                    Log.d("GAUTHAM", "Selected Cancel");
                    return true;
                default:
                    Log.d("GAUTHAM", "Selected Non");
                    return false;
            }
        }
    }

    public interface OnHistCardListener{
        void onHistCardClick(int position, View view);
    }

}
