package com.example.shopmaster.adapters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.shopmaster.R;
import com.example.shopmaster.datahandler.ChildItem;
import com.example.shopmaster.datahandler.DBServer;
import com.example.shopmaster.datahandler.Grocery;
import com.example.shopmaster.fragments.DraftListFragment;

import java.util.List;

public class ChildItemAdapter extends RecyclerView.Adapter<ChildItemAdapter.ChildViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private static final String KEY_CART = "cart";
    Context context;
    DBServer db;
    Grocery itemRemoving;
    private List<ChildItem> ChildItemList;

    // Constructor
    ChildItemAdapter(Context context, List<ChildItem> childItemList, Grocery itemRemoving)
    {
        this.context = context;
        this.ChildItemList = childItemList;
        this.itemRemoving = itemRemoving;
        db = new DBServer(context);
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(
            @NonNull ViewGroup viewGroup,
            int i)
    {

        // Here we inflate the corresponding
        // layout of the child item
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(
                        R.layout.layout_edit_child_items,
                        viewGroup, false);

        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder childViewHolder, int position)
    {

        ChildItem childItem = ChildItemList.get(position);
        childViewHolder.tvName.setText(childItem.getChildItemName());
        childViewHolder.tvPrice.setText("$"+childItem.getChildItemPrice());
        RequestOptions reqOpt = RequestOptions
                .fitCenterTransform()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(((ChildViewHolder)childViewHolder).iv_image.getWidth(),((ChildViewHolder)childViewHolder).iv_image.getHeight());
        Glide.with(context)
                .load(childItem.getChildItemImgUrl())
                .apply(reqOpt)
                .into(((ChildViewHolder)childViewHolder).iv_image);
        childViewHolder.cv_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Grocery itemAdding = db.findItemByName(childItem.getChildItemName()).get(0);
                if (itemRemoving!=null){
                    db.deleteItem(itemRemoving,KEY_CART);
                }
                db.addItem(itemAdding,KEY_CART);
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, DraftListFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(TAG)
                        .commit();

            }
        });


    }

    @Override
    public int getItemCount()
    {
        Log.d(TAG,"getItemCounts: "+ChildItemList.size());
        return ChildItemList.size();
    }

    // This class is to initialize
    // the Views present
    // in the child RecyclerView
    class ChildViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvName, tvPrice;
        ImageView iv_image;
        CardView cv_card;

        ChildViewHolder(View itemView)
        {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_edit_item_name);
            tvPrice = itemView.findViewById(R.id.tv_edit_item_price);
            iv_image = itemView.findViewById(R.id.img_child_item);
            cv_card = itemView.findViewById(R.id.cv_edit_card);
        }
    }
}