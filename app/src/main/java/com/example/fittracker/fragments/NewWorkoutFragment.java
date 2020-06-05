package com.example.fittracker.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fittracker.History;
import com.example.fittracker.R;
import com.example.fittracker.workout.NewWorkoutAdapter;
import com.example.fittracker.workout.PresetWorkout;
import com.example.fittracker.workout.Workout;
import com.example.fittracker.workout.currentWorkoutHistoryAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewWorkoutFragment extends Fragment {

    public static final String TAG  = "NewWorkoutFragment";
    private RecyclerView rvWorkouts;
    private RecyclerView rvWorkoutHistory;
    protected NewWorkoutAdapter newWorkoutAdapter;
    protected currentWorkoutHistoryAdapter currentWorkoutHistoryAdapter;
    protected Workout parentWorkout;

    protected List<History> currentWorkoutHistory;
    protected List<Workout> currentSets;
    protected List<PresetWorkout> presetWorkouts;
    protected TextView tvWorkoutName;
    protected EditText etWorkoutNote;
    protected TextView tvLabels;
    protected Button btnAddSet;
    protected Button btnFinish;
    protected TextView tvWorkoutHistoryText;
    protected String labelReps =     "Set            Weight        Reps";
    protected String labelDuration = "Set            Weight        Duration";
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
        if ( parentWorkout.getType().equals("repetition") ) {
            tvLabels.setText(labelReps);
        }
        else {
            tvLabels.setText(labelDuration);
        }

        btnAddSet = view.findViewById(R.id.btnAddSet);
        btnFinish = view.findViewById(R.id.btnSubmitWorkout);
        tvWorkoutHistoryText = view.findViewById(R.id.tvWorkoutHistory);
        tvWorkoutHistoryText.setText(workoutHistory);

        rvWorkoutHistory=view.findViewById(R.id.rvWorkOutHistory); //Recycler view for workout history identified

        rvWorkouts = view.findViewById(R.id.rvWorkoutSets);
        currentWorkoutHistory= new ArrayList<>();
        getCurrentWorkoutHistory();
        currentSets = new ArrayList<>();
        presetWorkouts = new ArrayList<>();
        getPresetWorkouts();
        newWorkoutAdapter = new NewWorkoutAdapter(getContext(), currentSets);
        currentWorkoutHistoryAdapter=new currentWorkoutHistoryAdapter(getContext(),currentWorkoutHistory);
        rvWorkoutHistory.setAdapter(currentWorkoutHistoryAdapter);
        rvWorkoutHistory.setLayoutManager(new LinearLayoutManager(getContext()));
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

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( currentSets.size() == 0 ) {
                    Toast.makeText(getContext(), "No Workout was Added", Toast.LENGTH_SHORT).show();
                }
                else {
                    pushDataToParse();
                }
                returnToTopFragment();
            }
        });
    }

    private void getCurrentWorkoutHistory() {
        ParseQuery<History> query= ParseQuery.getQuery(History.class);
        query.include(History.KEY_USER);
        query.whereEqualTo(History.KEY_USER, ParseUser.getCurrentUser());
        query.addDescendingOrder(History.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<History>() {
            @Override
            public void done(List<History> histories, ParseException e) {

                for(int i=0; i<histories.size(); i++){
                    if(histories.get(i).getName().equals(parentWorkout.getName())){
                        currentWorkoutHistory.add(histories.get(i));
                        Log.i(TAG,"history: "+ currentWorkoutHistory);

                    }
                }
                currentWorkoutHistoryAdapter.notifyDataSetChanged();
            }
        });
    }

    public void getPresetWorkouts() {
        ParseQuery<PresetWorkout> query = ParseQuery.getQuery(PresetWorkout.class);
        query.whereContains(PresetWorkout.KEY_NAME, parentWorkout.getName());
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

    public void pushDataToParse(){
        History history = new History();
        ParseUser user = ParseUser.getCurrentUser();
        Date date = new Date();
            Log.i(TAG, date.toString());
        ArrayList<Integer> reps = new ArrayList<>();

        history.setName(parentWorkout.getName());
        history.setUser(user);
        history.setSets(currentSets.size());
        history.setType(parentWorkout.getType());
        history.setWeight(currentSets.get(0).getWeight());
        history.setWorkoutDay(date);

        if (parentWorkout.getType().equals("repetition")) {
            for ( Workout workout : currentSets) {
                reps.add(workout.getReps());
            }
            history.setKeyReps(reps);
        }
        else {
            String duration = (currentSets.get(0).getDuration().split(" "))[0];
            history.setDuration(Integer.decode(duration));
        }

        history.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if ( e != null ) {
                    Log.e(TAG, "Error Pushing to Parse");
                    Toast.makeText(getContext(), "There was an error pushing to the server", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Successfully Pushed to Parse");
            }
        });
    }

    private void returnToTopFragment() {
        AppCompatActivity activity = (AppCompatActivity) btnFinish.getContext();
        Fragment fragment = new AddWorkoutFragment();
        activity.getSupportFragmentManager().beginTransaction().
                replace(R.id.mainContainer, fragment).addToBackStack(null).commit();
    }
}
