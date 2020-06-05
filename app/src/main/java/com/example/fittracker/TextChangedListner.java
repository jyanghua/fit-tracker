package com.example.fittracker;

import android.text.Editable;
import android.text.TextWatcher;

import com.example.fittracker.workout.Workout;

public class TextChangedListner<Workout> implements TextWatcher {

    private Workout workout;

    public TextChangedListner (Workout workout) {
            this.workout = workout;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
