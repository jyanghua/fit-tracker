package com.example.fittracker.workout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fittracker.R;
import com.parse.ParseFile;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder> {

    private Context context;
    List<AvailableWorkouts> availableWorkouts;

    public WorkoutAdapter (Context context, List<AvailableWorkouts> availableWorkouts) {
        this.context = context;
        this.availableWorkouts = availableWorkouts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.add_new_workout_options, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutAdapter.ViewHolder holder, int position) {
        AvailableWorkouts workout = availableWorkouts.get(position);
        holder.bind(workout);
    }

    @Override
    public int getItemCount() {
        return availableWorkouts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvWorkoutName;
        private ImageButton ibWorkout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvWorkoutName = itemView.findViewById(R.id.tvWorkout);
            ibWorkout = itemView.findViewById(R.id.ibWorkout);
        }

        public void bind(AvailableWorkouts workoutAvailable) {
            tvWorkoutName.setText(workoutAvailable.getKeyName());
            ParseFile image = workoutAvailable.getKeyImage();
            if ( image != null ) {
                Glide.with(context).load(image.getUrl()).into(ibWorkout);
            }
        }
    }
}
