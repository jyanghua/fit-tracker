package com.example.fittracker.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fittracker.R;
import com.example.fittracker.workout.NewWorkoutAdapter;
import com.example.fittracker.workout.PresetWorkout;
import com.example.fittracker.workout.Workout;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewWorkoutFragment extends Fragment {

    public static final String TAG  = "NewWorkoutFragment";
    private RecyclerView rvWorkouts;
    private RecyclerView rvWorkoutHistory;
    protected NewWorkoutAdapter newWorkoutAdapter;
    protected Workout parentWorkout;
    protected List<Workout> currentSets;
    protected List<PresetWorkout> presetWorkouts;
    protected TextView tvWorkoutName;
    protected EditText etWorkoutNote;
    protected TextView tvLabels;
    protected Button btnAddSet;
    protected TextView tvWorkoutHistoryText;
    protected String labelReps =     "Set            Reps            Weight";
    protected String labelDuration = "Set            Duration        Weight";
    protected String workoutHistory = "Workout History";

    public NewWorkoutFragment () {
        //Empty Constructor Fragment
    }

    public NewWorkoutFragment (String name, String type) {
        parentWorkout = new Workout(name, type);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_workout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvWorkoutName = view.findViewById(R.id.tvNameOfWorkout);
        tvWorkoutName.setText(parentWorkout.getName());

        etWorkoutNote = view.findViewById(R.id.etWorkoutNote);

        tvLabels = view.findViewById(R.id.tvLabels);
        if ( parentWorkout.getType().equals("reps") ) {
            tvLabels.setText(labelReps);
        }
        else {
            tvLabels.setText(labelDuration);
        }

        btnAddSet = view.findViewById(R.id.btnAddSet);
        tvWorkoutHistoryText = view.findViewById(R.id.tvWorkoutHistory);
        tvWorkoutHistoryText.setText(workoutHistory);

        rvWorkouts = view.findViewById(R.id.rvWorkoutSets);
        currentSets = new ArrayList<>();
        presetWorkouts = new ArrayList<>();
        getPresetWorkouts();
        newWorkoutAdapter = new NewWorkoutAdapter(getContext(), currentSets);

        rvWorkouts.setAdapter(newWorkoutAdapter);
        rvWorkouts.setLayoutManager(new LinearLayoutManager(getContext()));

        btnAddSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSets.add(new Workout(parentWorkout.getName(), parentWorkout.getType()));
                newWorkoutAdapter.updateCurrentSets(currentSets);
                Log.i(TAG, "A new workout set was added.");
                newWorkoutAdapter.notifyDataSetChanged();
            }
        });
    }

    public void getPresetWorkouts() {
        ParseQuery<PresetWorkout> query = ParseQuery.getQuery(PresetWorkout.class);

        query.findInBackground(new FindCallback<PresetWorkout>() {
            @Override
            public void done(List<PresetWorkout> objects, ParseException e) {

                if ( e != null ) {
                    Log.e(TAG, "Issue getting presetWorkouts", e);
                }

                for (PresetWorkout presetWorkout : objects) {
                    Log.i(TAG, "PreSet Workout: " + presetWorkout.getKeyName());
                }
                presetWorkouts.addAll(objects);
                newWorkoutAdapter.getPresetWorkouts().addAll(objects);
            }
        });
    }
}
