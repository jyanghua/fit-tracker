package com.example.fittracker.workout;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.lang.reflect.Array;

@ParseClassName("PresetWorkouts")
public class PresetWorkout extends ParseObject {

    public static final String KEY_NAME = "name";
    public static final String KEY_SETS = "sets";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_REPS = "reps";
    public static final String KEY_WEIGHT = "weight";
    public static final String KEY_TYPE = "type";

    public String getKeyName() {
        return getString(KEY_NAME);
    }

    public int getKeySets() {
        return getInt(KEY_SETS);
    }

    public String getKeyDuration() {
        return getString(KEY_DURATION);
    }

    public Array getKeyReps(){
        return (Array) get(KEY_REPS);
    }

    public String getKeyWeight() {
        return getString(KEY_WEIGHT);
    }

    public String getKeyType() {
        return getString(KEY_TYPE);
    }

}
