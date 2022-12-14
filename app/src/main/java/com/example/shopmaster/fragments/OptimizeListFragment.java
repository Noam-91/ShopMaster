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
    private final static String KEY_CART = "cart";
    private final static String KEY_NEWLIST = "newlist";
    private List<Grocery> shopList;
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
        if (bundle!= null) {
        }
        db = new DBServer(getContext());

        shopList = db.findAllItemsInTable(KEY_NEWLIST);

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
            numberPicker.setOnLongPressUpdateInterval(600);
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

    /**
     * Click Events
     * @param view : view
     */
    public void onClick(View view){
        Bundle bundle = new Bundle();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        switch (view.getId()){
            case R.id.btn_optimizelist_back:
                fragmentManager.popBackStack("NewListFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case R.id.btn_optimizelist_next:
                // Optimize Plan
                PlanCalculator calculator = new PlanCalculator(getContext(),primaryFactor,numOfStops);
                try {
                    List<Grocery> shopList = calculator.calculate();
                    db.clearCart();
                    db.addList(shopList,KEY_CART);
                    // Fragment transformation.
                    fragmentManager.beginTransaction()
                            .replace(R.id.navHostFragment, DraftListFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack(TAG)
                            .commit();
                } catch (Exception e) {
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}