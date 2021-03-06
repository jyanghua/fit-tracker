package com.example.fittracker.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fittracker.R;
import com.example.fittracker.workout.WorkoutAdapter;
import com.example.fittracker.workout.AvailableWorkouts;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddWorkoutFragment extends Fragment {

    public static final String TAG = "AddWorkoutFragment";
    private RecyclerView rvWorkouts;
    protected WorkoutAdapter wAdapter;
    protected List<AvailableWorkouts> allWorkouts;
    private Activity hostActivity;

    public AddWorkoutFragment() {
        //Required Empty Constructor
    }

    public AddWorkoutFragment (Activity hostActivity) {
        this.hostActivity = hostActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add_workout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvWorkouts = view.findViewById(R.id.rvWorkouts);
        allWorkouts = new ArrayList<>();
        wAdapter = new WorkoutAdapter(getContext(), allWorkouts, hostActivity);

        rvWorkouts.setAdapter(wAdapter);
        rvWorkouts.setLayoutManager(new LinearLayoutManager(getContext()));
        getWorkoutsFromParse();

    }

    protected void getWorkoutsFromParse () {
        ParseQuery<AvailableWorkouts> query = ParseQuery.getQuery(AvailableWorkouts.class);

        query.findInBackground(new FindCallback<AvailableWorkouts>() {
            @Override
            public void done(List<AvailableWorkouts> availableWorkouts, ParseException e) {
                if ( e != null ) {
                    Log.e(TAG, "Issue getting Available Workouts", e);
                }
                for (AvailableWorkouts workoutAvailable : availableWorkouts) {
                    Log.i(TAG, "Workout: " + workoutAvailable.getKeyName());
                }
                allWorkouts.addAll(availableWorkouts);
                wAdapter.notifyDataSetChanged();
            }
        });
    }

}
