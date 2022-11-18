package com.example.shopmaster.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmaster.R;
import com.example.shopmaster.datahandler.DBServer;
import com.example.shopmaster.datahandler.Grocery;

import java.util.List;


public class NewListAdapter extends RecyclerView.Adapter{
    private static final String TAG = NewListAdapter.class.getSimpleName();
    private static final String EMPTY_LIST_HINT="The cart is empty now.";
    private static final String KEY_NEWLIST="newlist";
    private final Context mContext;
    private DBServer db;
    private List<Grocery> shopList;
    private LayoutInflater mInflater;

    public NewListAdapter(Context context, List<Grocery>shopList){
        this.mContext = context;
        this.shopList = shopList;
        db = new DBServer(mContext);
        Log.d(TAG,"shopList length = "+shopList.size());

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG,"Draft List View Holder activated.");
        RecyclerView.ViewHolder viewHolder;
        mInflater = LayoutInflater.from(mContext);
        viewHolder = new NewListAdapter.ItemHolder(mInflater.inflate(R.layout.layout_newlist_items, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        Grocery item = shopList.get(pos);
        String name = item.getName();
        Integer quantity = item.getQuantity();
        ((NewListAdapter.ItemHolder) holder).tv_name.setText(name);
        ((NewListAdapter.ItemHolder) holder).tv_quantity.setText(String.valueOf(quantity));

        ((NewListAdapter.ItemHolder)holder).btn_inc.setOnClickListener(view -> {
            shopList.get(pos).setQuantity(quantity+1);
            db.updateItemQuantity(item,KEY_NEWLIST,quantity+1);
            notifyItemChanged(pos);
        });
        ((NewListAdapter.ItemHolder)holder).btn_dec.setOnClickListener(view -> {
            // quantity=0, delete item.
            if(quantity==1){
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle("Delete Item");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    shopList.remove(item);
                    db.deleteItem(item,KEY_NEWLIST);
                    notifyItemRemoved(pos);
                    notifyDataSetChanged();
                });
                alert.setNegativeButton(android.R.string.no, (dialog, which) -> {
                    // close dialog
                    dialog.cancel();
                });
                alert.show();
            }else{
                shopList.get(pos).setQuantity(quantity-1);
                db.updateItemQuantity(item,KEY_NEWLIST,quantity-1);
                notifyItemChanged(pos);
            }
        });
    }

    @Override
    public int getItemCount() {

        return shopList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name,tv_quantity;
        public Button btn_inc, btn_dec;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ItemHolder extends DraftListAdapter.ViewHolder {

        public ItemHolder(final View viewHolder) {
            super(viewHolder);
            tv_name = viewHolder.findViewById(R.id.tv_newlist_name);
            tv_quantity= viewHolder.findViewById(R.id.tv_newlist_quantity);
            btn_inc= viewHolder.findViewById(R.id.btn_newlist_increment);
            btn_dec= viewHolder.findViewById(R.id.btn_newlist_decrement);
        }
    }
}
