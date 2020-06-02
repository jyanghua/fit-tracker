package com.example.fittracker;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {


    private Context context;
    private List<History> histories;

    public HistoryAdapter(Context context, List<History> histories) {
        this.context = context;
        this.histories = histories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_history,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History history= histories.get(position);
        holder.bind(history);
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvDate;
        private TextView tvName;
        private TextView tvReps;
        private TextView tvSets;
        private TextView tvDuration;
        private TextView tvWeight;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate= itemView.findViewById(R.id.tvDate);
            tvName=itemView.findViewById(R.id.tvName);
            tvDuration= itemView.findViewById(R.id.tvDuration);
            tvReps= itemView.findViewById(R.id.tvReps);
            tvSets= itemView.findViewById(R.id.tvSets);
            tvWeight= itemView.findViewById(R.id.tvWeight);
        }

        public void bind(History history) {
            tvDate.setText(history.getWorkoutDay());
            tvName.setText("Name: "+history.getName());
            tvSets.setText("Sets: "+history.getSets());

            if(history.getDuration()==0){
                tvDuration.setText("Duration: N/A");
                tvReps.setText("Reps: "+history.getReps());
            }else{
                tvDuration.setText("Duration: "+ history.getDuration());
                tvReps.setText("Reps: N/A");
            }
            if (history.getWeight()==0){
                tvWeight.setText("Weight: N/A");
            }else{
                tvWeight.setText("Weight: "+ history.getWeight());
            }
        }
    }
}
