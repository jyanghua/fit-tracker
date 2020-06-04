package com.example.fittracker.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fittracker.History;
import com.example.fittracker.HistoryParentAdapter;
import com.example.fittracker.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HistoryFragment extends Fragment {

    public static final String TAG = "HistoryFragment";
    private RecyclerView rvHistory;
    protected HistoryParentAdapter adapter;
    protected List<History> allHistory;
    private List<String> dates;

    public HistoryFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvHistory=view.findViewById(R.id.rvHistory);
        allHistory=new ArrayList<>();

        dates =new ArrayList<>();
        initData();
        adapter= new HistoryParentAdapter(getContext(), dates, allHistory);


        rvHistory.setAdapter(adapter);
        rvHistory.setLayoutManager(new LinearLayoutManager((getContext())));

    }

    private void initData() {
        ParseQuery<History> query= ParseQuery.getQuery(History.class);
        query.include(History.KEY_USER);
        query.whereEqualTo(History.KEY_USER, ParseUser.getCurrentUser());
        query.addDescendingOrder(History.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<History>() {
            @Override
            public void done(List<History> histories, ParseException e) {
                allHistory.addAll(histories);
                Log.i(TAG,"history: "+ allHistory);

                for(int i=0; i<histories.size();i++){
                    dates.add(histories.get(i).getWorkoutDay());}
                Set<String> uniqueDates=new HashSet<String>(dates);
                dates.clear();
                dates.addAll(uniqueDates);
                Collections.sort(dates, Collections.<String>reverseOrder()); //sorts unique dates by descending order
                Log.i(TAG,"Dates: "+ dates+" Unique Dates "+uniqueDates); //Creates a list of unique dates
                adapter.notifyDataSetChanged();
            }
        });
    }



}
