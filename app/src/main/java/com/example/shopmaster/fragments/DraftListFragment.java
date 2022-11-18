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
import com.example.shopmaster.adapters.DraftListAdapter;
import com.example.shopmaster.datahandler.DBServer;
import com.example.shopmaster.datahandler.Grocery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DraftListFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private static final String KEY_CART = "cart";
    private List<Grocery> shopList;
    DBServer db;

    private RecyclerView rv;
    private Button btnSave, btnNext, btnBack, btnAdd;

    public DraftListFragment() {
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
        View view = inflater.inflate(R.layout.fragment_draft_list, container, false);
        rv = view.findViewById(R.id.rv_draftlist);
        btnSave = view.findViewById(R.id.btn_draftlist_save);
        btnNext = view.findViewById(R.id.btn_draftlist_next);
        btnBack = view.findViewById(R.id.btn_draftlist_back);
        btnAdd = view.findViewById(R.id.btn_draftlist_add);

        shopList = db.findAllItemsInTable(KEY_CART);
        List<Object> storeShopList = organizeGroceriesByStore(shopList);

        DraftListAdapter adapter = new DraftListAdapter(getContext(),storeShopList);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);
        ((SimpleItemAnimator) rv.getItemAnimator()).setSupportsChangeAnimations(false);

        btnSave.setOnClickListener(this::onClick);
        btnNext.setOnClickListener(this::onClick);
        btnBack.setOnClickListener(this::onClick);
        btnAdd.setOnClickListener(this::onClick);

        return view;
    }

    public void onClick(View view){
        Bundle bundle = new Bundle();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        switch (view.getId()){
            case R.id.btn_draftlist_save:
                Toast.makeText(getContext(),
                        "Your shopping list has been saved.",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_draftlist_next:
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, FinalListFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(TAG)
                        .commit();
                break;
            case R.id.btn_draftlist_back:
                fragmentManager.popBackStack("OptimizeListFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case R.id.btn_draftlist_add:
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, EditFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(TAG)
                        .commit();
                break;
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