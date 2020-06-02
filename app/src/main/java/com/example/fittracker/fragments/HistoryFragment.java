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
import com.example.fittracker.HistoryAdapter;
import com.example.fittracker.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HistoryFragment extends Fragment {

    public static final String TAG = "HistoryFragment";
    private RecyclerView rvHistory;
    private HistoryAdapter adapter;
    private List<History> allHistory;
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
        adapter= new HistoryAdapter(getContext(), allHistory);
        rvHistory.setAdapter(adapter);
        rvHistory.setLayoutManager(new LinearLayoutManager((getContext())));
        queryHistory();
    }

    private void queryHistory() {
        ParseQuery<History> query= ParseQuery.getQuery(History.class);
        query.include(History.KEY_USER);
        query.addDescendingOrder(History.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<History>() {
            @Override
            public void done(List<History> histories, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue with getting history", e);
                    return;
                }
                for (History history: histories){
                    Log.i(TAG,"Name:"+ history.getName()+" Sets: " + history.getSets()+" Reps: " +history.getReps()+ " Duration: "+history.getDuration()
                    + " Type: "+history.getType()+ " Weight: "+ history.getWeight() + " User: "+ history.getUser()+" Date: "+history.getWorkoutDay());
                }
                Log.i(TAG, "Histories" + histories +" history position 1"+ histories.get(0).getName());

                allHistory.addAll(histories);


                dates =new ArrayList<>();
                for(int i=0; i<histories.size();i++){
                    dates.add(histories.get(i).getWorkoutDay());
                    //Test: get specific information from one type of workout
//                    if(histories.get(i).getName().equals("Push-Ups")){
//                        allHistory.add(histories.get(i));
//                    }

                }

                Set<String> uniqueDates=new HashSet<String>(dates);
                Log.i(TAG,"Dates: "+ dates+" Unique Dates "+uniqueDates); //Creates a list of unique dates

                adapter.notifyDataSetChanged();
            }
        });
    }


}
