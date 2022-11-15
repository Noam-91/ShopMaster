package com.example.shopmaster.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.shopmaster.R;
import com.example.shopmaster.datahandler.DBServer;
import com.example.shopmaster.datahandler.Grocery;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewListFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private static final String KEY_NEW_SHOPPING_LIST_NAME = "New Shopping List Name";
    private static final String KEY_NEW_SHOPPING_LIST_QUANTITY = "New Shopping List Quantity";
    private final static String KEY_CART = "cart";
    private static List<String> keywordList = new ArrayList<>();
    private static List<Integer> quantityList = new ArrayList<>();
    private DBServer db;

    private TextView mTv_title;

    public NewListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle!= null) {
            keywordList = bundle.getStringArrayList(KEY_NEW_SHOPPING_LIST_NAME);
            quantityList = bundle.getIntegerArrayList(KEY_NEW_SHOPPING_LIST_QUANTITY);
        }
        db = new DBServer(getContext());

        // TEST !!!
        TEST_randomCart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_list, container, false);
        mTv_title = view.findViewById(R.id.tv_newlist_title);

        mTv_title.setOnClickListener(this::onClick);
        return view;
    }

    public void onClick(View view) {
        Bundle bundle = new Bundle();
        FragmentManager fragmentManager = getParentFragmentManager();
        OptimizeListFragment optimizeListFragment = new OptimizeListFragment();
        bundle.putStringArrayList(KEY_NEW_SHOPPING_LIST_NAME, (ArrayList<String>) keywordList);
        bundle.putIntegerArrayList(KEY_NEW_SHOPPING_LIST_QUANTITY, (ArrayList<Integer>) quantityList);
        optimizeListFragment.setArguments(bundle);
        Log.d(TAG,"Passing bundle to OptListFrag with length = "+keywordList.size());
        fragmentManager.beginTransaction()
                .replace(R.id.navHostFragment, optimizeListFragment, null)
                .setReorderingAllowed(true)
                .addToBackStack("new list") // name can be null
                .commit();
    }

    public void TEST_randomCart(){
        db.clearCart();
        Grocery randomItem = db.findItemById(1,"-1","grocery");
        keywordList.add(randomItem.getName());
        quantityList.add(new Random().nextInt(10));
        randomItem = db.findItemById(20,"-1","grocery");
        keywordList.add(randomItem.getName());
        quantityList.add(new Random().nextInt(10));
        randomItem = db.findItemById(50,"-1","grocery");
        keywordList.add(randomItem.getName());
        quantityList.add(new Random().nextInt(10));
        randomItem = db.findItemById(71,"-1","grocery");
        keywordList.add(randomItem.getName());
        quantityList.add(new Random().nextInt(10));
        randomItem = db.findItemById(121,"-1","grocery");
        keywordList.add(randomItem.getName());
        quantityList.add(new Random().nextInt(10));
        randomItem = db.findItemById(180,"-1","grocery");
        keywordList.add(randomItem.getName());
        quantityList.add(new Random().nextInt(10));
        randomItem = db.findItemById(229,"-1","grocery");
        keywordList.add(randomItem.getName());
        quantityList.add(new Random().nextInt(10));
        randomItem = db.findItemById(320,"-1","grocery");
        keywordList.add(randomItem.getName());
        quantityList.add(new Random().nextInt(10));
        randomItem = db.findItemById(380,"-1","grocery");
        keywordList.add(randomItem.getName());
        quantityList.add(new Random().nextInt(10));
    }

}