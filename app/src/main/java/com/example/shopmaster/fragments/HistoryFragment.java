package com.example.shopmaster.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmaster.R;
import com.example.shopmaster.adapters.HistoryAdapter;
import com.example.shopmaster.datahandler.DBServer;
import com.example.shopmaster.datahandler.Grocery;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private final String TAG = getClass().getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Spinner dateDropDown;

    private RecyclerView buyAgainList;

    List<Grocery> historyList;

    List<String> titles;
    List<String> prices;
    List<String> images;

    List<String> itemNames;
    List<String> itemPrices;
    List<String> imageUrls;
    List<String> stores;


    HistoryAdapter adapter;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment search.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, param1);
        bundle.putString(ARG_PARAM2, param2);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("SHOPPING_HISTORY", "Shopping History page created.");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        buyAgainList = view.findViewById(R.id.history_list);
        dateDropDown = (Spinner) view.findViewById(R.id.date_dropdown);


        DBServer db = new DBServer(getContext());
        historyList = db.findAllItemsInTable("grocery");

        itemNames = getItemNames(historyList);
        itemPrices = getItemPrices(historyList);
        imageUrls = getImageUrls(historyList);
        stores = getStores(historyList);

        titles = new ArrayList<>();
        prices = new ArrayList<>();
        images = new ArrayList<>();


        adapter = new HistoryAdapter(getContext(), itemNames, itemPrices, stores, imageUrls);

        GridLayoutManager gridLayoutManager;
        gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        buyAgainList.setLayoutManager(gridLayoutManager);
        buyAgainList.setAdapter(adapter);

        return view;
    }

    private List<String> getItemNames(List<Grocery> histList){
        List<String> names = new ArrayList<>();
        for (int i=0; i<histList.size(); i++){
            names.add(histList.get(i).getName());
        }
        return names;
    }

    private List<String> getItemPrices(List<Grocery> histList){
        List<String> prices = new ArrayList<>();
        for (int i=0; i<histList.size(); i++){
            prices.add(histList.get(i).getPrice());
        }
        return prices;
    }

    private List<String> getImageUrls(List<Grocery> histList){
        List<String> images = new ArrayList<>();
        for (int i=0; i<histList.size(); i++){
            images.add(histList.get(i).getImgUrl());
        }
        return images;
    }

    private List<String> getStores(List<Grocery> histList){
        List<String> stores = new ArrayList<>();
        for (int i=0; i<histList.size(); i++){
            stores.add(histList.get(i).getStore());
        }
        return stores;
    }
}