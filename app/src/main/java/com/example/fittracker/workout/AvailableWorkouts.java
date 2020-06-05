package com.example.fittracker.workout;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("AvailableWorkouts")
public class AvailableWorkouts extends ParseObject {

    public static final String KEY_NAME = "name";
    public static final String KEY_TYPE = "type";
    public static final String KEY_IMAGE = "WorkoutImage";

    public String getKeyName () {
        return getString(KEY_NAME);
    }
    public void setKeyName(String keyName) {
        put(KEY_NAME, keyName);
    }

    public String getKeyType () {
        return getString(KEY_TYPE);
    }

    public void setKeyType(String type) {
        put(KEY_TYPE, type);
    }

    public ParseFile getKeyImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setKeyImage (ParseFile keyImage) {
        put(KEY_IMAGE, keyImage);
    }
}
