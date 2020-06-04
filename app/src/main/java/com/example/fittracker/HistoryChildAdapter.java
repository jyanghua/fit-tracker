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


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
        }

        public void bind(History history) {
            tvName.setText(history.getName());

        }
    }
}
