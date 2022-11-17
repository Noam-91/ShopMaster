package com.example.shopmaster.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ListAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shopmaster.R;

import java.util.Arrays;
import java.util.List;

public class SearchFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    List<String> allKeywords;

    private SearchView searchView;
    private ListView listView;
    private Button btnBack;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        allKeywords = getAllKeywords();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        listView = view.findViewById(R.id.lv_search);
        searchView = view.findViewById(R.id.sv_search);

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