package com.example.shopmaster.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.shopmaster.DraftListActivity;
import com.example.shopmaster.OptimizeListActivity;
import com.example.shopmaster.R;
import com.example.shopmaster.datahandler.DBServer;
import com.example.shopmaster.datahandler.Grocery;
import com.example.shopmaster.datahandler.PlanCalculator;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OptimizeListFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private static final String KEY_NEW_SHOPPING_LIST_NAME = "New Shopping List Name";
    private static final String KEY_NEW_SHOPPING_LIST_QUANTITY = "New Shopping List Quantity";
    private final static String KEY_CART = "cart";
    private static List<String> keywordList = new ArrayList<>();
    private static List<Integer> quantityList = new ArrayList<>();
    private String primaryFactor = "time";
    private int numOfStops = 1;
    DBServer db;

    private Button btnBack, btnNext;
    private TabLayout tabLayout;
    private NumberPicker numberPicker;

    public OptimizeListFragment() {
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
        Log.d(TAG, "Get shop list with length = "+keywordList.size());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_optimize_list, container, false);
        btnBack = view.findViewById(R.id.btn_optimizelist_back);
        btnNext = view.findViewById(R.id.btn_optimizelist_next);
        tabLayout = view.findViewById(R.id.tabLayout_optimizelist);
        numberPicker = view.findViewById(R.id.np_optimizelist);

        btnBack.setOnClickListener(this::onClick);
        btnNext.setOnClickListener(this::onClick);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        Toast.makeText(getContext(), "Save time", Toast.LENGTH_SHORT).show();
                        primaryFactor="time";
                        Log.d(TAG,"Primary Factor: "+primaryFactor);
                        break;
                    case 1:
                        primaryFactor="money";
                        Log.d(TAG,"Primary Factor: "+primaryFactor);
                        Toast.makeText(getContext(), "Save money", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (numberPicker!=null){
            numberPicker.setMinValue(1);
            numberPicker.setMaxValue(4);
            numberPicker.setWrapSelectorWheel(false);
            numberPicker.isVerticalFadingEdgeEnabled();
            numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    numOfStops=newVal;
                    Log.d(TAG,"Number of stops: "+newVal);
                }
            });

        }

        return view;

    }


    public void onClick(View view){
        Bundle bundle = new Bundle();
        FragmentManager fragmentManager = getParentFragmentManager();
        switch (view.getId()){
            case R.id.btn_optimizelist_back:
                NewListFragment newListFragment = new NewListFragment();
                bundle.putStringArrayList(KEY_NEW_SHOPPING_LIST_NAME, (ArrayList<String>) keywordList);
                bundle.putIntegerArrayList(KEY_NEW_SHOPPING_LIST_QUANTITY, (ArrayList<Integer>) quantityList);
                newListFragment.setArguments(bundle);

                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, newListFragment, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("optimize list")
                        .commit();
                break;
            case R.id.btn_optimizelist_next:
                Log.d(TAG,"key word list: "+keywordList.toString());
                // Optimize Plan
                PlanCalculator calculator = new PlanCalculator(getContext(),keywordList,quantityList,primaryFactor,numOfStops);
                List<Grocery> shopList = calculator.calculate();
                try {
                    db.clearCart();
                    db.addList(shopList,KEY_CART);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Fragment transformation.
                DraftListFragment draftListFragment = new DraftListFragment();
                bundle.putStringArrayList(KEY_NEW_SHOPPING_LIST_NAME, (ArrayList<String>) keywordList);
                bundle.putIntegerArrayList(KEY_NEW_SHOPPING_LIST_QUANTITY, (ArrayList<Integer>) quantityList);
                draftListFragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, draftListFragment, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("optimize list")
                        .commit();
                break;
        }
    }
}