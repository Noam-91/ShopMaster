package com.example.shopmaster.fragments;

import android.os.Bundle;


import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shopmaster.R;
import com.example.shopmaster.datahandler.DBServer;
import com.example.shopmaster.datahandler.Grocery;
import java.util.List;

public class SearchFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    List<String> allKeywords;
    private FragmentManager fragmentManager;
    private final String KEY_NEWLIST = "newlist";

    private SearchView searchView;
    private ListView listView;
    private Button btnBack;
    private DBServer db;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle!= null) {
        }
        db = new DBServer(getContext());
        allKeywords = Grocery.getAllKeywords();
        fragmentManager = getActivity().getSupportFragmentManager();
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
                fragmentManager.popBackStack("NewListFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemName = (String)parent.getItemAtPosition(position);
                Grocery item = new Grocery();
                item.setName(itemName);
                item.setQuantity(1);
                db.addItem(item,KEY_NEWLIST);
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
                if (allKeywords.contains(query.toLowerCase())) {
                    searchAdapter.getFilter().filter(query);
                } else {
                    // Search query not found in List View
                    Toast.makeText(getContext(), "Not found", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return view;
    }


}