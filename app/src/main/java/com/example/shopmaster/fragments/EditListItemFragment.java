package com.example.shopmaster.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.shopmaster.R;
import com.example.shopmaster.adapters.FinalListAdapter;
import com.example.shopmaster.datahandler.DBServer;
import com.example.shopmaster.datahandler.Grocery;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class EditListItemFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private List<Grocery> shopList;
    DBServer db;

    RecyclerView rv;
    Button btnDone,btnBack;

    public EditListItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBServer(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return null;
    }

    public void onClick(View view){

//        case searchview:
//          get user typed input, call method below
//        case item:
//          change the item on the draft list to the one clicked
//          end edit_list activity
    }

    /**
     * Categorize the items by Store,
     * return an object list for the recyclerView adapter.
     * @param shopList : shopList
     * @return : Sorted list with Stores name included.
     */
//    TODO: change name
    public List<Object> organizeGroceriesByStore(List<Grocery> shopList) {

        /**
         * fetch user typed input and string-compare with database items
         * get the closest relevant matches (limited to 20 items total?)
         * return the match items in increasing price order per their store
         * return the match stores in increasing distance (Target -> County Market)
         */

        return null;
    }

}