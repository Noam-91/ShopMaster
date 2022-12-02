package com.example.shopmaster.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmaster.R;
import com.example.shopmaster.adapters.HistoryAdapter;
import com.example.shopmaster.datahandler.DBServer;
import com.example.shopmaster.datahandler.Grocery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment implements HistoryAdapter.OnHistCardListener, AdapterView.OnItemSelectedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private final String TAG = getClass().getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Spinner dateDropDown;

    private RecyclerView buyAgainList;

    List<Grocery> historyList;

    final private String OLD_DATE_FORMAT = "yyyy-MM-d";
    final private String NEW_DATE_FORMAT = "dd MMM, yyyy - E";

    List<List<String>> itemList;


    HistoryAdapter adapter;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment search.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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

        Log.d("SHOPPING_HISTORY", "Shopping History page created.");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        buyAgainList = view.findViewById(R.id.history_list);
        dateDropDown = (Spinner) view.findViewById(R.id.date_dropdown);

        DBServer db = new DBServer(getContext());

//        Uncomment the below Function Call to populate History table
        TEST_addItemsToHistoryTable();

//        historyList = db.findAllItemsInTable("history");
        historyList = db.findPopularHistoryItems();

        Log.d("SHOPPING_HISTORY", "Entries in History table : "+ historyList.size());

        itemList = getItems(historyList);

        // For populating the Spinner where the Dates will be dropped down
        SimpleDateFormat sdf;
        try{
            ArrayAdapter<String> dropDownAdapter;
            ArrayList<String> unique_dates = new ArrayList<String>(new HashSet<String>((ArrayList<String>) itemList.get(7)));
            ArrayList<Date> history_dates = new ArrayList<>();
            for (String date: unique_dates){
                sdf = new SimpleDateFormat(OLD_DATE_FORMAT);
                Date d = sdf.parse(date);
                history_dates.add(d);
            }
            Collections.sort(history_dates, Collections.reverseOrder());

            unique_dates = new ArrayList<>();
            for (Date d: history_dates){
                sdf = new SimpleDateFormat(NEW_DATE_FORMAT);
                unique_dates.add(sdf.format(d));
            }
            unique_dates.add(0, "All Dates");

            dropDownAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, unique_dates);
            dropDownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dateDropDown.setSelection(0);
            dateDropDown.setAdapter(dropDownAdapter);
            dateDropDown.setOnItemSelectedListener(this);
        } catch(Exception e){
            Log.d("SHOPPING_HISTORY", "ERROR: Issue creating adapter for Spinner. Check whether ItemLists has data.");
        }
        setShoppingHistoryLayout(itemList);
        return view;
    }

    private void setShoppingHistoryLayout(List<List<String>> itemList){
        adapter = new HistoryAdapter(
                getContext(),
                itemList,
                this);

        GridLayoutManager gridLayoutManager;
        gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        buyAgainList.setLayoutManager(gridLayoutManager);
        buyAgainList.setAdapter(adapter);
    }

    private List<List<String>> getItems(List<Grocery> histList){

        List<String> ids = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> cates = new ArrayList<>();
        List<String> prices = new ArrayList<>();
        List<String> stores = new ArrayList<>();
        List<String> imgurls = new ArrayList<>();
        List<String> qts = new ArrayList<>();
        List<String> dates = new ArrayList<>();

        for (int i=0; i<histList.size(); i++){
            ids.add(String.valueOf(histList.get(i).getId()));
            names.add(histList.get(i).getName());
            cates.add(histList.get(i).getCate());
            prices.add(histList.get(i).getPrice());
            stores.add(histList.get(i).getStore());
            imgurls.add(histList.get(i).getImgUrl());
            qts.add(String.valueOf(histList.get(i).getQuantity()));
            dates.add(histList.get(i).getHistDate());
        }

        List<List<String>> finalResult = new ArrayList<>();

        finalResult.add(ids);
        finalResult.add(names);
        finalResult.add(cates);
        finalResult.add(prices);
        finalResult.add(stores);
        finalResult.add(imgurls);
        finalResult.add(qts);
        finalResult.add(dates);

        return finalResult;

    }

    private String[] getUniqueDates(ArrayList<String> dates){
        ArrayList<String> tempList = new ArrayList<String>();
        String[] result = new String[dates.size()];
        for(int i = 0; i<dates.size(); i++){
            result[i] = dates.get(i);
        }
        return result;
    }



    @Override
    public void onHistCardClick(int position, View view) {
//        Navigate to new Activity
        Grocery selectedItem = historyList.get(position);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String date = adapterView.getItemAtPosition(position).toString();
        DBServer db = new DBServer(getContext());
        List<Grocery> date_data;
        if (position!=0) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(NEW_DATE_FORMAT);
                Date d = sdf.parse(date);
                sdf = new SimpleDateFormat(OLD_DATE_FORMAT);
                String req_d = sdf.format(d);
                date = req_d;
            } catch (ParseException e) {
                Log.d("SHOPPING_HISTORY", "Error while trying to change Date Format");
                e.printStackTrace();
            }
            date_data = db.findAllByDate(date);
            itemList = getItems(date_data);
        }
        else{
//            date_data = db.findAllItemsInTable("history");
            date_data = db.findPopularHistoryItems();
            itemList = getItems(date_data);
        }
        setShoppingHistoryLayout(itemList);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }




    private void TEST_addItemsToHistoryTable(){

        DBServer db = new DBServer(getContext());

        Grocery randomItem;
        randomItem = db.findItemById(7, "-1", "grocery");
        randomItem.setHistDate("2022-10-4");
        db.addItem(randomItem, "history");
        randomItem = db.findItemById(8, "-1", "grocery");
        randomItem.setHistDate("2022-10-4");
        db.addItem(randomItem, "history");
        randomItem = db.findItemById(9, "-1", "grocery");
        randomItem.setHistDate("2022-10-4");
        db.addItem(randomItem, "history");
        randomItem = db.findItemById(68, "-1", "grocery");
        randomItem.setHistDate("2022-10-4");
        db.addItem(randomItem, "history");
        randomItem = db.findItemById(45, "-1", "grocery");
        randomItem.setHistDate("2022-09-3");
        db.addItem(randomItem, "history");
        randomItem = db.findItemById(33, "-1", "grocery");
        randomItem.setHistDate("2022-09-3");
        db.addItem(randomItem, "history");
        randomItem = db.findItemById(111, "-1", "grocery");
        randomItem.setHistDate("2022-09-3");
        db.addItem(randomItem, "history");
        randomItem = db.findItemById(112, "-1", "grocery");
        randomItem.setHistDate("2022-09-23");
        db.addItem(randomItem, "history");
        randomItem = db.findItemById(113, "-1", "grocery");
        randomItem.setHistDate("2022-09-23");
        db.addItem(randomItem, "history");

        randomItem = db.findItemById(114, "-1", "grocery");
        randomItem.setHistDate("2022-08-11");
        db.addItem(randomItem, "history");
        randomItem = db.findItemById(115, "-1", "grocery");
        randomItem.setHistDate("2022-10-14");
        db.addItem(randomItem, "history");
    }
}