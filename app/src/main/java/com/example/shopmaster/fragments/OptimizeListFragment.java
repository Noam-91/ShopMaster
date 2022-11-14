package com.example.shopmaster.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopmaster.OptimizeListActivity;
import com.example.shopmaster.R;
import com.example.shopmaster.datahandler.DBServer;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OptimizeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OptimizeListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final String TAG = getClass().getSimpleName();
    private final String KEY_NEW_SHOPPING_LIST = "New Shopping List";
    private List<String> keywordList = new ArrayList<>();
    private String primaryFactor = "time";
    private int numOfStops = 1;
    DBServer db;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OptimizeListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OptimizeListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OptimizeListFragment newInstance(String param1, String param2) {
        OptimizeListFragment fragment = new OptimizeListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, param1);
        bundle.putString(ARG_PARAM2, param2);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_optimize_list, container, false);

    }


    public void onClick(View view){
        Bundle bundle = new Bundle();
        switch (view.getId()){
            case R.id.btn_optimizelist_back:
                NewListFragment newListFragment = new NewListFragment();
                bundle.putStringArrayList(KEY_NEW_SHOPPING_LIST, (ArrayList<String>) keywordList);
                newListFragment.setArguments(bundle);

                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.navHostFragment, NewListFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // name can be null
                        .commit();

        }
    }
}