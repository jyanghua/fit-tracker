package com.example.fittracker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class HistoryParentAdapter extends RecyclerView.Adapter<HistoryParentAdapter.ViewHolder> {

    public static final String TAG="HistoryParentAdapter";
    protected List<String> dates;
    protected List<History> allHistory;
    protected HistoryChildAdapter childAdapter;
    private RecyclerView rvChild;
    private Context context;




    public HistoryParentAdapter(Context context, List<String> dates) {
        this.dates = dates;
        this.context= context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.item_history,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String date=dates.get(position);

        allHistory=new ArrayList<>();
        initData();
        childAdapter =new HistoryChildAdapter(context, allHistory);


        holder.tvDate.setText(date);
        holder.rvChild.setAdapter(childAdapter);
        childAdapter.notifyDataSetChanged();
    }

    private void initData() {
        ParseQuery<History> query2 = ParseQuery.getQuery(History.class);
        query2.include(History.KEY_USER);
        query2.addDescendingOrder(History.KEY_CREATED_AT);
        query2.findInBackground(new FindCallback<History>() {
            @Override
            public void done(List<History> histories, ParseException e) {
//                allHistory.addAll(histories);
                for(int i=0; i<histories.size();i++){
                    Log.i(TAG,"history: "+ histories.get(i).getWorkoutDay());

                    if(histories.get(i).getWorkoutDay().equals("06-03-2020")){ //Hardcoded the date to check if worokouts are done on this date
                        allHistory.add(histories.get(i));
                    }
                }
                Log.i(TAG,"history size: " + allHistory.size());

            }
        });
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate;
        RecyclerView rvChild;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=itemView.findViewById(R.id.tvDate);
            rvChild=itemView.findViewById(R.id.rvChild);
        }


    }
}
