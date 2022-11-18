package com.example.shopmaster.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.shopmaster.R;
import com.example.shopmaster.SettingsActivity;
import com.example.shopmaster.datahandler.DBServer;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private final static String KEY_CART = "cart";
    DBServer db;

    private ImageButton ibtnSettings;
    private Button btnNewlist;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBServer(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ibtnSettings = view.findViewById(R.id.ibtn_home_settings);
        btnNewlist = view.findViewById(R.id.btn_home_newlist);


        ibtnSettings.setOnClickListener(this::onClick);
        btnNewlist.setOnClickListener(this::onClick);
        return view;
    }

    public void onClick(View view){
        Bundle bundle = new Bundle();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        switch (view.getId()){
            case R.id.btn_home_newlist:
                // If previous cart exist pop delete alert
                if (!db.tableIsEmpty(KEY_CART)){discardExistedCartAlert ();}

                // Switch Navigation tab
                BottomNavigationView navView = getActivity().findViewById(R.id.bottomNav_view);
                navView.setSelectedItemId(R.id.navigation_cart);

                // transfer fragment.
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, NewListFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(TAG)
                        .commit();
                break;

            case R.id.ibtn_home_settings:
                Intent intent = new Intent();
                intent.setClass(getActivity(), SettingsActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }

    public void discardExistedCartAlert (){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Reset Cart");
        alert.setMessage("Are you sure you want to discard existed cart?");
        alert.setPositiveButton(android.R.string.yes, (dialog, which) -> {
            db.clearCart();
        });
        alert.setNegativeButton(android.R.string.no, (dialog, which) -> {
            // close dialog
            dialog.cancel();
        });
        alert.show();
    }
}