package com.example.shopmaster.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmaster.R;
import com.example.shopmaster.adapters.NewListAdapter;
import com.example.shopmaster.datahandler.DBServer;
import com.example.shopmaster.datahandler.Grocery;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class NewListFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private static final String KEY_NEW_SHOPPING_LIST_NAME = "New Shopping List Name";
    private static final String KEY_NEW_SHOPPING_LIST_QUANTITY = "New Shopping List Quantity";
    private static List<String> keywordList = new ArrayList<>();
    private static List<Integer> quantityList = new ArrayList<>();
    private DBServer db;
    private BottomSheetDialog popularItems;
    private FragmentManager fragmentManager;

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

        Log.d(TAG, "onCreate: keywordList length = "+keywordList.size());
        db = new DBServer(getContext());
        fragmentManager = getParentFragmentManager();

        // TEST !!!
//        TEST_randomCart();
//        TEST_shortList();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(KEY_NEW_SHOPPING_LIST_NAME, (ArrayList<String>) keywordList);
        outState.putIntegerArrayList(KEY_NEW_SHOPPING_LIST_QUANTITY, (ArrayList<Integer>) quantityList);
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
        btnPopular = view.findViewById(R.id.btn_newlist_popular);
        searchView = view.findViewById(R.id.sv_newlist);
        rv = view.findViewById(R.id.rv_newlist);

        popularItems = new BottomSheetDialog(getContext());

        btnNext.setOnClickListener(this::onClick);
        btnSave.setOnClickListener(this::onClick);
        btnPopular.setOnClickListener(this::onClick);
        btnDiscard.setOnClickListener(this::onClick);
        btnHistory.setOnClickListener(this::onClick);
        searchView.setOnClickListener(this::onClick);
        searchView.onActionViewCollapsed();

        NewListAdapter adapter = new NewListAdapter(getContext(),keywordList,quantityList);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.addItemDecoration(getRecyclerViewDivider(R.drawable.inset_recyclerview_divider));
        rv.setAdapter(adapter);

        //TODO: new list in Home cleans up db cart but not the string list.

        return view;
    }

    public void onClick(View view) {
        Bundle bundle = new Bundle();

        switch (view.getId()){
            case R.id.btn_newlist_next:
                // Save to DB Cart.
                saveListToDB(keywordList,quantityList);
                // Switch fragment.
                OptimizeListFragment optimizeListFragment = new OptimizeListFragment();
                bundle.putStringArrayList(KEY_NEW_SHOPPING_LIST_NAME, 
                        (ArrayList<String>) keywordList);
                bundle.putIntegerArrayList(KEY_NEW_SHOPPING_LIST_QUANTITY, 
                        (ArrayList<Integer>) quantityList);
                optimizeListFragment.setArguments(bundle);
                Log.d(TAG,"Passing bundle to OptListFrag with length = "+keywordList.size());
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, optimizeListFragment, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(TAG)
                        .commit();
                break;
            case R.id.btn_draftlist_save:
                Toast.makeText(getContext(),"Your shopping cart has been saved.",
                        Toast.LENGTH_SHORT).show();
                saveListToDB(keywordList,quantityList);
                break;
            case R.id.btn_newlist_discard:
                discardExistedCartAlert();
                break;
            case R.id.btn_newlist_history:
                // Switch Navigation tab
                BottomNavigationView navView = getActivity().findViewById(R.id.bottomNav_view);
                navView.setSelectedItemId(R.id.navigation_history);

                // transfer fragment.
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, HistoryFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(TAG)
                        .commit();
                break;
            case R.id.btn_newlist_popular:
                Log.d(TAG,"popular button clicked");
                showPopularItems();
                break;
            case R.id.sv_newlist:
                SearchFragment searchFragment = new SearchFragment();
                bundle.putStringArrayList(KEY_NEW_SHOPPING_LIST_NAME,
                        (ArrayList<String>) keywordList);
                bundle.putIntegerArrayList(KEY_NEW_SHOPPING_LIST_QUANTITY,
                        (ArrayList<Integer>) quantityList);
                searchFragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, SearchFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(TAG)
                        .commit();
        }

    }

    public RecyclerView.ItemDecoration getRecyclerViewDivider(@DrawableRes int drawableId) {
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull(ResourcesCompat.getDrawable(getResources(), drawableId, null)));
        return itemDecoration;
    }

    public void showPopularItems(){
        popularItems.setContentView(R.layout.layout_newlist_popularitems);

        LinearLayout btnBeef = popularItems.findViewById(R.id.ll_newlist_popular_beef);
        LinearLayout btnChicken = popularItems.findViewById(R.id.ll_newlist_popular_chicken);
        LinearLayout btnShrimp = popularItems.findViewById(R.id.ll_newlist_popular_shrimp);
        LinearLayout btnBread = popularItems.findViewById(R.id.ll_newlist_popular_bread);
        LinearLayout btnToast = popularItems.findViewById(R.id.ll_newlist_popular_toast);
        LinearLayout btnApple = popularItems.findViewById(R.id.ll_newlist_popular_apple);
        LinearLayout btnBanana = popularItems.findViewById(R.id.ll_newlist_popular_banana);
        LinearLayout btnTomato = popularItems.findViewById(R.id.ll_newlist_popular_tomato);
        LinearLayout btnCorn = popularItems.findViewById(R.id.ll_newlist_popular_corn);

        //setOnClickListener
        btnBeef.setOnClickListener(this::onClickPopular);
        btnChicken.setOnClickListener(this::onClickPopular);
        btnShrimp.setOnClickListener(this::onClickPopular);
        btnBread.setOnClickListener(this::onClickPopular);
        btnToast.setOnClickListener(this::onClickPopular);
        btnApple.setOnClickListener(this::onClickPopular);
        btnBanana.setOnClickListener(this::onClickPopular);
        btnTomato.setOnClickListener(this::onClickPopular);
        btnCorn.setOnClickListener(this::onClickPopular);


        //in onClick, popularItems.dismiss() to close dialog.
        popularItems.show();
    }
    public void onClickPopular(View view) {
        switch (view.getId()){
            case R.id.ll_newlist_popular_beef:
                keywordList.add("Beef");
                quantityList.add(1);
                popularItems.dismiss();
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, NewListFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
                break;
            case R.id.ll_newlist_popular_chicken:
                Log.d(TAG,"Add chicken.");
                keywordList.add("Chicken");
                quantityList.add(1);
                popularItems.dismiss();
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, NewListFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
                break;
            case R.id.ll_newlist_popular_shrimp:
                keywordList.add("Shrimp");
                quantityList.add(1);
                popularItems.dismiss();
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, NewListFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
                break;
            case R.id.ll_newlist_popular_bread:
                keywordList.add("Bread");
                quantityList.add(1);
                popularItems.dismiss();
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, NewListFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
                break;
            case R.id.ll_newlist_popular_toast:
                keywordList.add("Toast");
                quantityList.add(1);
                popularItems.dismiss();
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, NewListFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
                break;
            case R.id.ll_newlist_popular_apple:
                keywordList.add("Apple");
                quantityList.add(1);
                popularItems.dismiss();
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, NewListFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
                break;
            case R.id.ll_newlist_popular_banana:
                keywordList.add("Banana");
                quantityList.add(1);
                popularItems.dismiss();
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, NewListFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
                break;
            case R.id.ll_newlist_popular_tomato:
                keywordList.add("Tomato");
                quantityList.add(1);
                popularItems.dismiss();
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, NewListFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
                break;
            case R.id.ll_newlist_popular_corn:
                keywordList.add("Corn");
                quantityList.add(1);
                popularItems.dismiss();
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, NewListFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
                break;
        }
    }

    public void TEST_shortList(){
        keywordList.clear();
        quantityList.clear();
        String[] itemNames = {"Banana", "Beef", "Bread"};
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

    private void discardExistedCartAlert (){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Reset Cart");
        alert.setMessage("Are you sure you want to discard existed cart?");
        alert.setPositiveButton(android.R.string.yes, (dialog, which) -> {
            db.clearCart();
            keywordList.clear();
            quantityList.clear();                    
        });
        alert.setNegativeButton(android.R.string.no, (dialog, which) -> {
            // close dialog
            dialog.cancel();
        });
        alert.show();
    }
    
    public void saveListToDB(List<String> keywordList,List<Integer> quantityList){
        List<Grocery> shopList= new ArrayList<>();
        for (int i=0; i<keywordList.size(); i++){
            Grocery item = new Grocery();
            item.setName(keywordList.get(i));
            item.setQuantity(quantityList.get(i));
            shopList.add(item);
        }
        try {
            db.clearCart();
            db.addList(shopList,"cart");
        } catch (IOException e) {
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT);
        }
    }

}