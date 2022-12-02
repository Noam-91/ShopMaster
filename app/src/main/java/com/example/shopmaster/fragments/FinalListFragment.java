package com.example.shopmaster.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.shopmaster.R;
import com.example.shopmaster.adapters.FinalListAdapter;
import com.example.shopmaster.datahandler.DBServer;
import com.example.shopmaster.datahandler.Grocery;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class FinalListFragment extends Fragment {
    private static final String KEY_CART = "cart";
    private final String TAG = getClass().getSimpleName();
    private List<Grocery> shopList;
    DBServer db;

    RecyclerView rv;
    Button btnDone,btnBack;

    public FinalListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBServer(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_final_list, container, false);
        rv = view.findViewById(R.id.rv_finallist);
        btnDone = view.findViewById(R.id.btn_finallist_done);
        btnBack = view.findViewById(R.id.btn_finallist_back);

        shopList = db.findAllItemsInTable(KEY_CART);
        List<Object> storeShopList = organizeGroceriesByStore(shopList);
        FinalListAdapter adapter = new FinalListAdapter(getContext(),storeShopList);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);
        ((SimpleItemAnimator) rv.getItemAnimator()).setSupportsChangeAnimations(false);

        btnDone.setOnClickListener(this::onClick);
        btnBack.setOnClickListener(this::onClick);

        return view;
    }

    public void onClick(View view){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Bundle bundle = new Bundle();
        switch (view.getId()){
            case R.id.btn_finallist_done:
                //Save shopping history.
                Date date = new java.sql.Date(System.currentTimeMillis());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d");
                String h_date = sdf.format(date);
                for (Grocery item : shopList){
                    item.setHistDate(h_date);
                    db.addItem(item,"history");
                }
                Toast.makeText(getContext(), "Your shopping history on "+date+" has been saved",
                        Toast.LENGTH_SHORT).show();
                // Reset Cart
                db.clearCart();
                db.clearNewList();
                //Reset Cart navigation to NewListFragment.
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, NewListFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
                // Switch to Home.
                BottomNavigationView navView = getActivity().findViewById(R.id.bottomNav_view);
                navView.setSelectedItemId(R.id.navigation_home);
                break;
            case R.id.btn_finallist_back:
                fragmentManager.popBackStack("DraftListFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    /**
     * Categorize the items by Store and return an object list for the recyclerView adapter.
     * @param shopList : shopList
     * @return : Sorted list with Stores name included.
     */
    public List<Object> organizeGroceriesByStore(List<Grocery> shopList){
        Log.d(TAG,"There are "+shopList.size()+" items to organize.");
        List<Object []> storeItemPairList = new ArrayList<>();
        Collections.sort(shopList, new Grocery.SortbyStoreCate());
        for (Grocery item : shopList){
            String store = item.getStore();
            Object[] storeItemPair ={store,item};
            storeItemPairList.add(storeItemPair);
        }
        List<Object> storeShopList = new ArrayList<>();

        for (Object[] pair : storeItemPairList){
            String store = (String)pair[0];
            Grocery item = (Grocery) pair[1];
            if(!storeShopList.contains(store)){
                storeShopList.add(store);
            }
            storeShopList.add(item);
        }

        Log.d(TAG,"There are "+storeShopList.size()+" items organized.");
        return storeShopList;
    }
}