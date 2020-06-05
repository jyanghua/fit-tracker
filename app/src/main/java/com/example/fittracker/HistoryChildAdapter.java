package com.example.fittracker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryChildAdapter extends RecyclerView.Adapter<HistoryChildAdapter.ViewHolder> {



    public static final String TAG="HistoryChildAdapter";

    private List<History> histories;
    private Context context;

    public HistoryChildAdapter(Context context, List<History> histories) {
        this.context=context;
        this.histories = histories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_history_data,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History history=histories.get(position);
        Log.i(TAG,"history: " + history);
        holder.bind(history);
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName;
        private TextView tvReps;
        private TextView tvSets;
        private TextView tvWeight;
        private TextView tvDuration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            tvReps=itemView.findViewById(R.id.tvReps);
            tvSets=itemView.findViewById(R.id.tvSets);
            tvWeight=itemView.findViewById(R.id.tvWeight);
            tvDuration=itemView.findViewById(R.id.tvDuration);

        }

        public void bind(History history) {
            tvName.setText(history.getName());
            tvSets.setText("Sets: "+history.getSets());
            if (history.getDuration() ==0){
                tvDuration.setText("Duration: N/A");
                tvReps.setText("Reps: "+ history.getReps());

            }else{
                tvDuration.setText("Duration: "+ history.getDuration());
                tvReps.setText("Reps: N/A");
            }

            if (history.getWeight()==0){
                tvWeight.setText("Weight: N/A");
            }else{
                tvWeight.setText("Weight: "+ history.getWeight()+"lbs");
            }

        }
    }
}
