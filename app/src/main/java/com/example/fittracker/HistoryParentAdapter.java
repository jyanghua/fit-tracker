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
    private List<History> currentHistory;




    public HistoryParentAdapter(Context context, List<String> dates, List<History> allHistory) {
        this.dates = dates;
        this.context= context;
        this.allHistory=allHistory;
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
        currentHistory=new ArrayList<>();
        Log.i(TAG,"history: "+ allHistory+"current date: " + date);
        filterDates(date);

        childAdapter =new HistoryChildAdapter(context, currentHistory);


        holder.tvDate.setText(date);
        holder.rvChild.setAdapter(childAdapter);
        childAdapter.notifyDataSetChanged();
    }

    private void filterDates(String date) {
        for(int i=0;i<allHistory.size();i++){
            Log.i(TAG, "allHistory: "+ allHistory.get(i));
            if(allHistory.get(i).getWorkoutDay().equals(date)){
                currentHistory.add(allHistory.get(i));
                Log.i(TAG,"true "+date+ "currentHistory" + currentHistory);
            }
        }
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
