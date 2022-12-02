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

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class NewListFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private static final String KEY_NEWLIST = "newlist";
    private List<Grocery> shopList;
    private DBServer db;


    private Button btnNext, btnSave, btnDiscard, btnHistory,btnPopular;
    private SearchView searchView;
    private RecyclerView rv;
    private BottomSheetDialog popularItems;
    private FragmentManager fragmentManager;

    public NewListFragment() {
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
        Log.d(TAG,"onCreate, shopList = "+shopList);

        fragmentManager = getActivity().getSupportFragmentManager();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG,"onCreateView.");
        View view = inflater.inflate(R.layout.fragment_new_list, container, false);
        btnNext = view.findViewById(R.id.btn_newlist_next);
        btnSave = view.findViewById(R.id.btn_newlist_save);
        btnDiscard = view.findViewById(R.id.btn_newlist_discard);
        btnHistory = view.findViewById(R.id.btn_newlist_history);
        btnPopular = view.findViewById(R.id.btn_newlist_popular);
        searchView = view.findViewById(R.id.sv_newlist);
        rv = view.findViewById(R.id.rv_newlist);

        popularItems = new BottomSheetDialog(getContext());

        shopList = db.findAllItemsInTable(KEY_NEWLIST);
        Log.d(TAG,"onCreate, shopList = "+shopList);

        btnNext.setOnClickListener(this::onClick);
        btnSave.setOnClickListener(this::onClick);
        btnPopular.setOnClickListener(this::onClick);
        btnDiscard.setOnClickListener(this::onClick);
        btnHistory.setOnClickListener(this::onClick);
        searchView.setOnClickListener(this::onClick);
        searchView.onActionViewCollapsed();


        NewListAdapter adapter = new NewListAdapter(getContext(),shopList);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.addItemDecoration(getRecyclerViewDivider(R.drawable.inset_recyclerview_divider));
        rv.setAdapter(adapter);

        return view;
    }

    public void onClick(View view) {
        Bundle bundle = new Bundle();

        switch (view.getId()){
            case R.id.btn_newlist_next:
                // Switch fragment.
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, OptimizeListFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(TAG)
                        .commit();
                break;
            case R.id.btn_newlist_save:
                Toast.makeText(getContext(),"Your shopping cart has been saved.",
                        Toast.LENGTH_SHORT).show();
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
        Grocery item = new Grocery();
        switch (view.getId()){
            case R.id.ll_newlist_popular_beef:
                item.setName("Beef");
                item.setQuantity(1);
                db.addItem(item,KEY_NEWLIST);
                popularItems.dismiss();
                refreshRecyclerView();
                break;
            case R.id.ll_newlist_popular_chicken:
                item.setName("Chicken");
                item.setQuantity(1);
                db.addItem(item,KEY_NEWLIST);
                popularItems.dismiss();
                refreshRecyclerView();
                break;
            case R.id.ll_newlist_popular_shrimp:
                item.setName("Shrimp");
                item.setQuantity(1);
                db.addItem(item,KEY_NEWLIST);
                popularItems.dismiss();
                refreshRecyclerView();
                break;
            case R.id.ll_newlist_popular_bread:
                item.setName("Bread");
                item.setQuantity(1);
                db.addItem(item,KEY_NEWLIST);
                popularItems.dismiss();
                refreshRecyclerView();
                break;
            case R.id.ll_newlist_popular_toast:
                item.setName("Toast");
                item.setQuantity(1);
                db.addItem(item,KEY_NEWLIST);
                popularItems.dismiss();
                refreshRecyclerView();
                break;
            case R.id.ll_newlist_popular_apple:
                item.setName("Apple");
                item.setQuantity(1);
                db.addItem(item,KEY_NEWLIST);
                popularItems.dismiss();
                refreshRecyclerView();
                break;
            case R.id.ll_newlist_popular_banana:
                item.setName("Banana");
                item.setQuantity(1);
                db.addItem(item,KEY_NEWLIST);
                popularItems.dismiss();
                refreshRecyclerView();
                break;
            case R.id.ll_newlist_popular_tomato:
                item.setName("Tomato");
                item.setQuantity(1);
                db.addItem(item,KEY_NEWLIST);
                popularItems.dismiss();
                refreshRecyclerView();
                break;
            case R.id.ll_newlist_popular_corn:
                item.setName("Corn");
                item.setQuantity(1);
                db.addItem(item,KEY_NEWLIST);
                popularItems.dismiss();
                refreshRecyclerView();
                break;
        }
    }

    public void TEST_shortList(){
        db.clearNewList();
        Grocery item = new Grocery();
        String[] itemNames = {"Banana", "Beef", "Bread"};
        for (String itemName : itemNames){
            item.setName(itemName);
            item.setQuantity(new Random().nextInt(10)+1);
            db.addItem(item,KEY_NEWLIST);
        }
        Log.d(TAG,"onCreate, shopList = "+db.findAllItemsInTable(KEY_NEWLIST).size());
    }

    public void TEST_randomCart(){
        db.clearNewList();
        Grocery item = new Grocery();
        Grocery randomItem = db.findItemById(1,"-1","grocery");
        randomItem.setQuantity(new Random().nextInt(10)+1);
        db.addItem(randomItem,KEY_NEWLIST);
        randomItem = db.findItemById(20,"-1","grocery");
        randomItem.setQuantity(new Random().nextInt(10)+1);
        db.addItem(randomItem,KEY_NEWLIST);
        randomItem = db.findItemById(50,"-1","grocery");
        randomItem.setQuantity(new Random().nextInt(10)+1);
        db.addItem(randomItem,KEY_NEWLIST);
        randomItem = db.findItemById(71,"-1","grocery");
        randomItem.setQuantity(new Random().nextInt(10)+1);
        db.addItem(randomItem,KEY_NEWLIST);
        randomItem = db.findItemById(121,"-1","grocery");
        randomItem.setQuantity(new Random().nextInt(10)+1);
        db.addItem(randomItem,KEY_NEWLIST);
        randomItem = db.findItemById(180,"-1","grocery");
        randomItem.setQuantity(new Random().nextInt(10)+1);
        db.addItem(randomItem,KEY_NEWLIST);
        randomItem = db.findItemById(229,"-1","grocery");
        randomItem.setQuantity(new Random().nextInt(10)+1);
        db.addItem(randomItem,KEY_NEWLIST);
        randomItem = db.findItemById(320,"-1","grocery");
        randomItem.setQuantity(new Random().nextInt(10)+1);
        db.addItem(randomItem,KEY_NEWLIST);
        randomItem = db.findItemById(380,"-1","grocery");
        randomItem.setQuantity(new Random().nextInt(10)+1);
        db.addItem(randomItem,KEY_NEWLIST);
    }

    private void discardExistedCartAlert (){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Reset Cart");
        alert.setMessage("Are you sure you want to discard existed cart?");
        alert.setPositiveButton(android.R.string.yes, (dialog, which) -> {
            db.clearNewList();
            refreshRecyclerView();
        });
        alert.setNegativeButton(android.R.string.no, (dialog, which) -> {
            // close dialog
            dialog.cancel();
        });
        alert.show();
    }

    private void refreshRecyclerView(){
        shopList = db.findAllItemsInTable(KEY_NEWLIST);
        NewListAdapter adapter = new NewListAdapter(getContext(),shopList);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.addItemDecoration(getRecyclerViewDivider(R.drawable.inset_recyclerview_divider));
        rv.setAdapter(adapter);
    }

}