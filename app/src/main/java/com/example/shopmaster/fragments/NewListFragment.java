package com.example.shopmaster.fragments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.shopmaster.MainActivity;
import com.example.shopmaster.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String KEY_KEYWORDLIST = "keyword list";
    private static final String ARG_PARAM2 = "param2";
    private final String TAG = getClass().getSimpleName();
    private static List<String> keywordList = new ArrayList<>();

    private TextView mTv_title;

    public NewListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment favourites.
     */
    // TODO: Rename and change types and number of parameters
    public static NewListFragment newInstance(String param1, String param2) {
        NewListFragment fragment = new NewListFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(KEY_KEYWORDLIST, (ArrayList<String>) keywordList);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            keywordList = bundle.getStringArrayList(KEY_KEYWORDLIST);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_newlist, container, false);
        mTv_title = view.findViewById(R.id.tv_newlist_title);

        mTv_title.setOnClickListener(this::onClick);
        return view;
    }

    public void onClick(View view) {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.navHostFragment, OptimizeListFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("new list") // name can be null
                .commit();
    }

}