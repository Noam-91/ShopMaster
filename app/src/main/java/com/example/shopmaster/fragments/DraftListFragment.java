package com.example.shopmaster.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopmaster.DraftListActivity;
import com.example.shopmaster.FinalListActivity;
import com.example.shopmaster.R;
import com.example.shopmaster.TestMainActivity;
import com.example.shopmaster.adapters.DraftListAdapter;
import com.example.shopmaster.datahandler.DBServer;
import com.example.shopmaster.datahandler.Grocery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DraftListFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private static final String KEY_NEW_SHOPPING_LIST_NAME = "New Shopping List Name";
    private static final String KEY_NEW_SHOPPING_LIST_QUANTITY = "New Shopping List Quantity";
    private final static String KEY_CART = "cart";
    private static List<String> keywordList = new ArrayList<>();
    private static List<Integer> quantityList = new ArrayList<>();
    private List<Grocery> shopList;
    DBServer db;

    private RecyclerView rv;
    private Button btnSave, btnNext;

    public DraftListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        Log.d(TAG,"bundle is empty? "+getArguments().isEmpty());
        if (bundle!= null) {
            keywordList = bundle.getStringArrayList(KEY_NEW_SHOPPING_LIST_NAME);
            quantityList = bundle.getIntegerArrayList(KEY_NEW_SHOPPING_LIST_QUANTITY);
        }
        db = new DBServer(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_draft_list, container, false);
        rv = view.findViewById(R.id.rv_draftlist);
        btnSave = view.findViewById(R.id.btn_draftlist_save);
        btnNext = view.findViewById(R.id.btn_draftlist_next);

        shopList = db.findAllItemsInTable("cart");
        List<Object> storeShopList = organizeGroceriesByStore(shopList);

        DraftListAdapter adapter = new DraftListAdapter(getContext(),storeShopList);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);
        ((SimpleItemAnimator) rv.getItemAnimator()).setSupportsChangeAnimations(false);

        btnSave.setOnClickListener(this::onClick);
        btnNext.setOnClickListener(this::onClick);
        //TODO: User need to add more at this moment.

        return view;
    }

    public void onClick(View view){
        Bundle bundle = new Bundle();
        FragmentManager fragmentManager = getParentFragmentManager();
        switch (view.getId()){
            case R.id.btn_draftlist_save:
                Toast.makeText(getContext(),
                        "Your shopping list has been saved.",Toast.LENGTH_SHORT).show();

                break;
            case R.id.btn_draftlist_next:
                FinalListFragment finalListFragment = new  FinalListFragment();
                bundle.putStringArrayList(KEY_NEW_SHOPPING_LIST_NAME, (ArrayList<String>) keywordList);
                bundle.putIntegerArrayList(KEY_NEW_SHOPPING_LIST_QUANTITY, (ArrayList<Integer>) quantityList);
                finalListFragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, finalListFragment, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("draft list")
                        .commit();
                break;
            case R.id.btn_draftlist_back:
                OptimizeListFragment optimizeListFragment = new  OptimizeListFragment();
                bundle.putStringArrayList(KEY_NEW_SHOPPING_LIST_NAME, (ArrayList<String>) keywordList);
                bundle.putIntegerArrayList(KEY_NEW_SHOPPING_LIST_QUANTITY, (ArrayList<Integer>) quantityList);
                optimizeListFragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, optimizeListFragment, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("draft list")
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