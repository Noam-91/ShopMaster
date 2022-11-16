package com.example.shopmaster.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopmaster.R;
import com.example.shopmaster.fragments.HistoryFragment;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final Context histContext;

    List<String> titles;
    List<String> prices;
    List<String> stores;
    List<String> images;
    LayoutInflater inflater;

    public HistoryAdapter(Context ctx,
                          List<String> titles,
                          List<String> prices,
                          List<String> stores,
                          List<String> images)
    {
        this.titles = titles;
        this.prices = prices;
        this.images = images;
        this.stores = stores;
        histContext = ctx;
        this.inflater = LayoutInflater.from(ctx);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.history_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(titles.get(position));
        holder.price.setText("$ "+prices.get(position));
        holder.store.setText(stores.get(position));
//        holder.image.setImageResource(images.get(position));
        Glide.with(histContext).load(images.get(position)).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView price;
        TextView store;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.buy_again_item_name);
            price = itemView.findViewById(R.id.buy_again_item_price);
            image = itemView.findViewById(R.id.buy_again_image);
            store = itemView.findViewById(R.id.hist_store_name);
        }
    }

}
