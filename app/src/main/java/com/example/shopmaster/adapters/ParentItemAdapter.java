package com.example.shopmaster.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shopmaster.R;
import com.example.shopmaster.datahandler.Grocery;
import com.example.shopmaster.datahandler.ParentItem;

import java.io.IOException;
import java.util.List;

public class ParentItemAdapter extends RecyclerView.Adapter<ParentItemAdapter.ParentViewHolder> {

    private String TAG = getClass().getSimpleName();
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<ParentItem> itemList;
    Grocery itemRemoving;
    Context context;

    public ParentItemAdapter(Context context, List<ParentItem> itemList,Grocery itemRemoving)
    {
        this.context = context;
        this.itemList = itemList;
        this.itemRemoving=itemRemoving;
    }

    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i)
    {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.layout_edit_child_container,viewGroup, false);
        return new ParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ParentViewHolder parentViewHolder,
            int position)
    {
        ParentItem parentItem = itemList.get(position);
        parentViewHolder.tvStore.setText(parentItem.getParentItemStore());

        // Get Travel time between stores.
        int travelTime=0;
        if(itemRemoving!=null){
            String startStore = itemRemoving.getStore();
            String endStore = parentItem.getParentItemStore();
            try {
                travelTime = Grocery.getTravalTime(startStore,endStore);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            parentViewHolder.tvExtraTime.setVisibility(View.INVISIBLE);
        }

        if (travelTime!=0){
            parentViewHolder.tvExtraTime.setText("+ "+travelTime+" min");
        }else{
            parentViewHolder.tvExtraTime.setVisibility(View.INVISIBLE);
        }
        // Load adapter for items in the store.
        LinearLayoutManager layoutManager= new LinearLayoutManager(parentViewHolder
                .ChildRecyclerView.getContext(),LinearLayoutManager.HORIZONTAL,false);
        Log.d(TAG, "ChildItemList size: "+parentItem.getChildItemList().size());
        layoutManager.setInitialPrefetchItemCount(parentItem.getChildItemList().size());
        ChildItemAdapter childItemAdapter = new ChildItemAdapter(context,parentItem.getChildItemList(),itemRemoving);
        parentViewHolder.ChildRecyclerView.setLayoutManager(layoutManager);
        parentViewHolder.ChildRecyclerView.setAdapter(childItemAdapter);
        parentViewHolder.ChildRecyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount()
    {
        return itemList.size();
    }

    // This class is to initialize
    // the Views present in
    // the parent RecyclerView
    class ParentViewHolder
            extends RecyclerView.ViewHolder {

        private TextView tvStore, tvExtraTime;
        private RecyclerView ChildRecyclerView;

        ParentViewHolder(final View itemView)
        {
            super(itemView);

            tvStore = itemView .findViewById( R.id.tv_edit_store);
            tvExtraTime = itemView.findViewById(R.id.tv_edit_extratime);
            ChildRecyclerView = itemView.findViewById(R.id.child_recyclerview);
        }
    }
}
