package com.example.shopmaster.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmaster.MainActivity;
import com.example.shopmaster.R;
import com.example.shopmaster.UserActivity;

import java.util.List;

public class UserButtonsAdapter extends RecyclerView.Adapter<UserButtonsAdapter.LinearViewHolder>{
    private Context mContext;
    private UserButtonsAdapter.OnItemClickListener mListener;

    public UserButtonsAdapter(Context context, UserButtonsAdapter.OnItemClickListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    public UserButtonsAdapter.LinearViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_user_rv, parent, false));
    }

    @Override
    public void onBindViewHolder(UserButtonsAdapter.LinearViewHolder holder, int position){
        holder.imageView.setImageResource(R.drawable.icon_history);
        holder.textView.setText("Shopping History");
        switch(position){
            case 1:
                holder.imageView.setImageResource(R.drawable.icon_account);
                holder.textView.setText("Shopping List");
                break;
            case 2:
                holder.imageView.setImageResource(R.drawable.icon_account);
                holder.textView.setText("Account");
                break;
            case 3:
                holder.imageView.setImageResource(R.drawable.icon_report);
                holder.textView.setText("Report");
                break;
            case 4:
                holder.imageView.setImageResource(R.drawable.icon_signout);
                holder.textView.setText("Sign Out");
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                switch(position) {
                    case 1:
                        intent = new Intent(holder.itemView.getContext(),MainActivity.class);
                        break;
                    case 2:
                        intent = new Intent(holder.itemView.getContext(),MainActivity.class);
                        break;
                    case 3:
                        intent = new Intent(holder.itemView.getContext(),MainActivity.class);
                        break;
                    case 4:
                        intent = new Intent(holder.itemView.getContext(),MainActivity.class);
                        break;
                }
                mContext.startActivity(intent);
                mListener.onClick(holder.textView.getText().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
//        return list.size();
        return 4;
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;

        public LinearViewHolder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_user_button);
            textView = itemView.findViewById(R.id.tv_user_button);
        }
    }
    public interface  OnItemClickListener{
        void onClick(String text);
    }

}