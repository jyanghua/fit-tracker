package com.example.fittracker;

import android.app.Application;

//import com.example.parstagram.models.Post;
import com.example.fittracker.workout.AvailableWorkouts;
import com.example.fittracker.workout.PresetWorkout;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(History.class);

        ParseObject.registerSubclass(AvailableWorkouts.class);
        ParseObject.registerSubclass(PresetWorkout.class);
        ParseObject.registerSubclass(History.class);
        // set applicationId, and server server based on the values in the Heroku settings.
        // clientKey is not needed unless explicitly configured
        // any network interceptors must be added with the Configuration Builder given this syntax
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("codepath-fit-tracker") // should correspond to APP_ID env variable
                .clientKey("CodepathFitTracker")  // set explicitly unless clientKey is explicitly configured on Parse server
                .server("https://codepath-fit-tracker.herokuapp.com/parse").build());
    }
}
