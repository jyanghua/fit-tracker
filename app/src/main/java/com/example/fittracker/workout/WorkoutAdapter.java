package com.example.fittracker.workout;

import android.app.Activity;
import android.app.AppComponentFactory;
import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.example.fittracker.MainActivity;
import com.example.fittracker.R;;
import com.example.fittracker.fragments.NewWorkoutFragment;
import com.parse.ParseFile;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder> {

    public static final String TAG = "WorkoutAdapter";
    private Context context;
    private Activity hostActivity;
    List<AvailableWorkouts> availableWorkouts;

    public WorkoutAdapter (Context context, List<AvailableWorkouts> availableWorkouts, Activity hostActivity) {
        this.context = context;
        this.availableWorkouts = availableWorkouts;
        this.hostActivity = hostActivity;
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

        private Button btnWorkoutName;
        private ImageButton ibWorkout;
        private String type;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            btnWorkoutName = itemView.findViewById(R.id.btnWorkoutName);
            ibWorkout = itemView.findViewById(R.id.ibWorkout);

            String tag = "AddNewWorkout";
            btnWorkoutName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment fragment = new NewWorkoutFragment(btnWorkoutName.getText().toString(), type);
                    activity.getSupportFragmentManager().beginTransaction().
                            replace(R.id.mainContainer, fragment).addToBackStack(null).commit();
                }
            });

            ibWorkout.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment fragment = new NewWorkoutFragment(btnWorkoutName.getText().toString(), type);
                    activity.getSupportFragmentManager().beginTransaction().
                            replace(R.id.mainContainer, fragment).addToBackStack(null).commit();
                }
            });

        }

        public void bind(AvailableWorkouts workoutAvailable) {
            btnWorkoutName.setText(workoutAvailable.getKeyName());
            type = workoutAvailable.getKeyType();
            ParseFile image = workoutAvailable.getKeyImage();
            if ( image != null ) {
                Glide.with(context).load(image.getUrl()).into(ibWorkout);
            }

            Log.i(TAG, "Workout Name: " + workoutAvailable.getKeyName() + " Workout Type: " + workoutAvailable.getKeyType());
        }
    }
}
