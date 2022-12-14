package com.example.shopmaster.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmaster.R;
import com.example.shopmaster.adapters.ParentItemAdapter;
import com.example.shopmaster.datahandler.ChildItem;
import com.example.shopmaster.datahandler.DBServer;
import com.example.shopmaster.datahandler.Grocery;
import com.example.shopmaster.datahandler.ParentItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.PriorityQueue;

public class EditFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private static final String KEY_ITEM_NAME = "item name";
    private static final String KEY_STORES = "list stores";
    private static final String KEY_CART = "cart";
    private String itemName = null;
    private ArrayList<String> existedStores = null;
    // in order of rows: Costco, County Market, Target, Walmart
    private String keyword = null;
    private DBServer db;
    Grocery itemRemoving = new Grocery();

    SearchView searchView;
    ImageButton btnBack;
    RecyclerView parentRecyclerView;

    public EditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            itemName = getArguments().getString(KEY_ITEM_NAME);
            existedStores = getArguments().getStringArrayList(KEY_STORES);
        }
        Log.d(TAG,"onCreate: get itemName: "+itemName);
        Log.d(TAG,"existedStores"+existedStores);

        db = new DBServer(getContext());
        // Extract itemName
         if (itemName==null){
                    itemName="";
         }else{
             String[] substrList =  itemName.split(" ");
             for (String substr: substrList){
                 for (String candidate : Grocery.getAllKeywords()){
                     if (substr.toLowerCase().contains(candidate) ){
                         keyword = candidate;
                         break;
                     }

                 }
             }
         }

        Log.d(TAG,"Get keyword: "+keyword);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        parentRecyclerView = view.findViewById(R.id.parent_recyclerview);
        btnBack = view.findViewById(R.id.btn_edit_back);
        searchView = view.findViewById(R.id.sv_edit);

        //Set Bottom Navigation invisible.
        BottomNavigationView navView = getActivity().findViewById(R.id.bottomNav_view);
        navView.setVisibility(View.INVISIBLE);

        // Keyword!=null: find alternative.
        // Keyword==null: search and add.
        if (keyword!=null){
            searchView.setQueryHint(keyword);
            // RecyclerView
            LinearLayoutManager layoutManager= new LinearLayoutManager(getContext());
            List<ParentItem> parentItemList = ParentItemList(keyword);
            ParentItemAdapter parentItemAdapter= new ParentItemAdapter(getContext(),
                    parentItemList,itemRemoving);
            parentRecyclerView.setAdapter(parentItemAdapter);
            parentRecyclerView.setLayoutManager(layoutManager);
        }else{
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // RecyclerView
                    LinearLayoutManager layoutManager= new LinearLayoutManager(getContext());
                    List<ParentItem> parentItemList = ParentItemList(query);
                    ParentItemAdapter parentItemAdapter= new ParentItemAdapter(getContext(),
                            parentItemList,null);
                    parentRecyclerView.setAdapter(parentItemAdapter);
                    parentRecyclerView.setLayoutManager(layoutManager);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }

        //back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                navView.setVisibility(View.VISIBLE);
                fragmentManager.popBackStack("DraftListFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

        return view;
    }

    private List<ParentItem> ParentItemList(String keyword)
    {
        List<Grocery> relatedItems = db.findItemByName(keyword.toLowerCase());
        List<ChildItem> targetList = new ArrayList<>();
        List<ChildItem> countyList = new ArrayList<>();
        List<ChildItem> walmartList = new ArrayList<>();
        List<ChildItem> costcoList = new ArrayList<>();
        for (Grocery item : relatedItems){
            ChildItem childItem = new ChildItem();
            if(item.getName().equals(itemName)){
                itemRemoving = item;
                continue;
            }
            switch (item.getStore()){
                case "Target":
                    childItem.setChildItemName(item.getName());
                    childItem.setChildItemPrice(item.getPrice());
                    childItem.setChildItemImgUrl(item.getImgUrl());
                    targetList.add(childItem);
                    break;
                case "County Market":
                    childItem.setChildItemName(item.getName());
                    childItem.setChildItemPrice(item.getPrice());
                    childItem.setChildItemImgUrl(item.getImgUrl());
                    countyList.add(childItem);
                    break;
                case "Walmart":
                    childItem.setChildItemName(item.getName());
                    childItem.setChildItemPrice(item.getPrice());
                    childItem.setChildItemImgUrl(item.getImgUrl());
                    walmartList.add(childItem);
                    break;
                case "Costco":
                    childItem.setChildItemName(item.getName());
                    childItem.setChildItemPrice(item.getPrice());
                    childItem.setChildItemImgUrl(item.getImgUrl());
                    costcoList.add(childItem);
                    break;
            }
        }

        List<ParentItem> itemList = new ArrayList<>();
        List<String> storeList = Arrays.asList(Grocery.getStoreList());

        int travelTime=0;
        if(itemRemoving!=null){
            String startStore = itemRemoving.getStore();
            for (String endStore:storeList){

                try {
                    travelTime = Grocery.getTravalTime(startStore,endStore);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (existedStores.contains(endStore)){travelTime=0;}
                if (endStore.equals("Target")&&!targetList.isEmpty()){
                    ParentItem parentItem= new ParentItem(endStore, targetList);
                    parentItem.setParentItemExtraTime(travelTime);
                    itemList.add(parentItem);
                    continue;
                }
                if (endStore.equals("County Market")&&!countyList.isEmpty()){
                    ParentItem parentItem= new ParentItem(endStore, countyList);
                    parentItem.setParentItemExtraTime(travelTime);
                    itemList.add(parentItem);
                    continue;
                }
                if (endStore.equals("Walmart")&&!walmartList.isEmpty()){
                    ParentItem parentItem= new ParentItem(endStore, walmartList);
                    parentItem.setParentItemExtraTime(travelTime);
                    itemList.add(parentItem);
                    continue;
                }
                if (endStore.equals("Costco")&&!costcoList.isEmpty()){
                    ParentItem parentItem= new ParentItem(endStore, costcoList);
                    parentItem.setParentItemExtraTime(travelTime);
                    itemList.add(parentItem);
                    continue;
                }
            }
            Collections.sort(itemList);
        }


        Log.d(TAG,"four store list length: "+targetList.size()+" "+countyList.size()+" "+walmartList.size()+" "+costcoList.size()+" ");
        Log.d(TAG,"first child list size: "+itemList.get(0).getChildItemList().size());
        return itemList;
    }

}