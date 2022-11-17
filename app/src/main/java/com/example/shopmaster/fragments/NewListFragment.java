package com.example.shopmaster.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmaster.R;
import com.example.shopmaster.datahandler.DBServer;
import com.example.shopmaster.datahandler.Grocery;
import com.google.android.material.bottomsheet.BottomSheetDialog;

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

    private Button btnNext, btnSave, btnDiscard, btnHistory,btnPopular;
    private SearchView searchView;
    private RecyclerView rv;

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
//        TEST_randomCart();
        TEST_shortList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_list, container, false);

        btnNext = view.findViewById(R.id.btn_newlist_next);
        btnSave = view.findViewById(R.id.btn_newlist_save);
        btnDiscard = view.findViewById(R.id.btn_newlist_discard);
        btnHistory = view.findViewById(R.id.btn_newlist_history);
        btnPopular = view.findViewById(R.id.btn_newlist_discard);

        btnNext.setOnClickListener(this::onClick);
        return view;
    }

    public void onClick(View view) {
        switch (getId()){
            case R.id.btn_newlist_next:
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
                break;
            case R.id.btn_draftlist_save:
                break;
            case R.id.btn_newlist_discard:
                break;
            case R.id.btn_newlist_history:
                break;
            case R.id.btn_newlist_popular:
                showPopularItems();
                break;
        }

    }

    public void showPopularItems(){
        final BottomSheetDialog popularItems = new BottomSheetDialog(getContext());
        popularItems.setContentView(R.layout.layout_newlist_popularitems);
        //TODO: findViewById
        //setOnClickListener
        //in onClick, popularItems.dismiss() to close dialog.
        popularItems.show();
    }

    public void TEST_shortList(){
        keywordList.clear();
        quantityList.clear();
        String[] itemNames = {"Banana", "Beef","Bread"};
        for (String itemName : itemNames){
            keywordList.add(itemName);
            quantityList.add(new Random().nextInt(10)+1);
        }
    }

    public void TEST_randomCart(){
        db.clearCart();
        keywordList.clear();
        quantityList.clear();
        Grocery randomItem = db.findItemById(1,"-1","grocery");
        keywordList.add(randomItem.getName());
        quantityList.add(new Random().nextInt(10)+1);
        randomItem = db.findItemById(20,"-1","grocery");
        keywordList.add(randomItem.getName());
        quantityList.add(new Random().nextInt(10)+1);
        randomItem = db.findItemById(50,"-1","grocery");
        keywordList.add(randomItem.getName());
        quantityList.add(new Random().nextInt(10)+1);
        randomItem = db.findItemById(71,"-1","grocery");
        keywordList.add(randomItem.getName());
        quantityList.add(new Random().nextInt(10)+1);
        randomItem = db.findItemById(121,"-1","grocery");
        keywordList.add(randomItem.getName());
        quantityList.add(new Random().nextInt(10)+1);
        randomItem = db.findItemById(180,"-1","grocery");
        keywordList.add(randomItem.getName());
        quantityList.add(new Random().nextInt(10)+1);
        randomItem = db.findItemById(229,"-1","grocery");
        keywordList.add(randomItem.getName());
        quantityList.add(new Random().nextInt(10)+1);
        randomItem = db.findItemById(320,"-1","grocery");
        keywordList.add(randomItem.getName());
        quantityList.add(new Random().nextInt(10)+1);
        randomItem = db.findItemById(380,"-1","grocery");
        keywordList.add(randomItem.getName());
        quantityList.add(new Random().nextInt(10)+1);
    }

}