package com.example.shopmaster.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shopmaster.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private static final String KEY_NEW_SHOPPING_LIST_NAME = "New Shopping List Name";
    private static final String KEY_NEW_SHOPPING_LIST_QUANTITY = "New Shopping List Quantity";
    private static List<String> keywordList = new ArrayList<>();
    private static List<Integer> quantityList = new ArrayList<>();
    List<String> allKeywords;
    private FragmentManager fragmentManager;

    private SearchView searchView;
    private ListView listView;
    private Button btnBack;

    public SearchFragment() {
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
        Log.d(TAG,"Search Created. shop list length: "+keywordList.size());
        allKeywords = getAllKeywords();
        fragmentManager = getParentFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        listView = view.findViewById(R.id.lv_search);
        searchView = view.findViewById(R.id.sv_search);
        btnBack = view.findViewById(R.id.btn_search_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                NewListFragment newListFragment = new NewListFragment();
                bundle.putStringArrayList(KEY_NEW_SHOPPING_LIST_NAME,
                        (ArrayList<String>) keywordList);
                bundle.putIntegerArrayList(KEY_NEW_SHOPPING_LIST_QUANTITY,
                        (ArrayList<Integer>) quantityList);
                newListFragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, NewListFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemName = (String)parent.getItemAtPosition(position);
                keywordList.add(itemName);
                quantityList.add(1);
                Bundle bundle = new Bundle();
                NewListFragment newListFragment = new NewListFragment();
                bundle.putStringArrayList(KEY_NEW_SHOPPING_LIST_NAME,
                        (ArrayList<String>) keywordList);
                bundle.putIntegerArrayList(KEY_NEW_SHOPPING_LIST_QUANTITY,
                        (ArrayList<Integer>) quantityList);
                newListFragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, NewListFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
            }
        });
        // SearchView filter
        searchView.setActivated(true);
        searchView.setQueryHint("Type your keyword here");
        searchView.onActionViewExpanded();
        searchView.setIconified(false);
        searchView.clearFocus();

        ArrayAdapter searchAdapter= new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,allKeywords);
        listView.setAdapter(searchAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // If the list contains the search query than filter the adapter
                // using the filter method with the query as its argument
                if (allKeywords.contains(query)) {
                    searchAdapter.getFilter().filter(query);
                } else {
                    // Search query not found in List View
                    Toast.makeText(getContext(), "Not found", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                searchAadapter.getFilter().filter(newText);
                return false;
            }
        });
        return view;
    }

    public List<String> getAllKeywords(){
        // TODO: Create keyword list
        String[] str = {"Beef","Banana","Bread"};
        return Arrays.asList(str);
    }
}