package com.example.fittracker.workout;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fittracker.R;
import com.example.fittracker.TextChangedListner;
import com.example.fittracker.fragments.NewWorkoutFragment;
import com.parse.ParseFile;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import kotlin.jvm.JvmOverloads;

public class NewWorkoutAdapter extends RecyclerView.Adapter<NewWorkoutAdapter.ViewHolder> {

    private Context context;
    private List<PresetWorkout> presetWorkouts;
    private List<Workout> currentWorkouts;

    public NewWorkoutAdapter(Context context, List<Workout> currentWorkouts) {
        this.context = context;
        this.currentWorkouts = currentWorkouts;
        this.presetWorkouts = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.add_set, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Workout workout = currentWorkouts.get(position);
        holder.bind(workout, position);
    }

    @Override
    public int getItemCount() {
        return currentWorkouts.size();
    }

    public List<PresetWorkout> getPresetWorkouts() {
        return this.presetWorkouts;
    }

    public List<Workout> getCurrentWorkouts() {
        return this.currentWorkouts;
    }

    public void updateCurrentSets(List<Workout> currentWorkouts) {
        this.currentWorkouts = currentWorkouts;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvsetNumber;
        private EditText etWeight;
        private EditText etrepsDuration;
        private Button btnDelete;
        private int index;

        public ViewHolder(View view) {
            super(view);
                tvsetNumber = view.findViewById(R.id.tvSets);
                etWeight = view.findViewById(R.id.etWeight);
                etrepsDuration = view.findViewById(R.id.etRepsDuration);
                btnDelete = view.findViewById(R.id.btnClear);

                etWeight.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if ( editable.toString().length() == 0 ) return;
                        currentWorkouts.get(index).setWeight(Integer.decode(editable.toString()));
                    }
                });

                etrepsDuration.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if ( editable.toString().length() == 0 ) return;
                        if (currentWorkouts.get(index).getType().equals("duration")) {
                            currentWorkouts.get(index).setDuration(editable.toString());
                        }
                        else {
                            currentWorkouts.get(index).addRep(Integer.decode(editable.toString()));
                        }
                    }
                });

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentWorkouts.remove(index);
                        notifyDataSetChanged();
                    }
                });
        }

        public void bind(Workout workout, int position) {
            tvsetNumber.setText(Integer.toString(position + 1));
            index = position;
            if (Integer.toString(workout.getWeight()).equals("")) {
                etWeight.setText(0);
            } else {
                etWeight.setText(Integer.toString(workout.getWeight()));
            }
            etrepsDuration.setText(workout.getDuration());
        }
    }
}
