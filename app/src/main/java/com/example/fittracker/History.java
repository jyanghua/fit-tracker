package com.example.fittracker;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ParseClassName("WorkoutHistory")
public class History extends ParseObject{

    public static final String KEY_SETS="Sets";
    public static final String KEY_REPS="Reps";
    public static final String KEY_TYPE="Type";
    public static final String KEY_NAME="Name";
    public static final String KEY_DURATION="Duration";
    public static final String KEY_WEIGHT="Weight";
    public static final String KEY_USER="user";
    public static final String KEY_DATE= "WorkoutDay";
    public static final String KEY_CREATED_AT="createdAt";


    public String getName(){
        return getString(KEY_NAME);
    }
    public void setName(String sets){
        put(KEY_NAME,sets);
    }

    public int getSets(){
        return getInt(KEY_SETS);
    }
    public void setReps(int reps) {put(KEY_SETS, reps);}

    public void setSets(int reps){
        put(KEY_REPS,reps);
    }

    public void setKeyReps(ArrayList<Integer> reps) {put(KEY_REPS, reps.toString());}

    public List<String> getReps(){
        return getList(KEY_REPS);
    }
//    public void setRets(int sets){
//        put(KEY_SETS,sets);
//    }
//
    public String getType(){
        return getString(KEY_TYPE);
    }
    public void setType(String type){
        put(KEY_TYPE, type);
    }

    public int getDuration(){
        return getInt(KEY_DURATION);
    }
    public void setDuration(int duration){
        put(KEY_SETS, duration);
    }

    public int getWeight(){
        return getInt(KEY_WEIGHT);
    }
    public void setWeight(int weight){
        put(KEY_WEIGHT, weight);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user) {put(KEY_USER, user);}

    public String getWorkoutDay(){
        Date date = getDate(KEY_DATE);
        DateFormat dateFormat= new SimpleDateFormat("MM-dd-yyyy");
        String strDate= dateFormat.format(date);
        return strDate;
    }

    public void setWorkoutDay(Date workoutDay) {
        put(KEY_DATE, workoutDay);
    }
}
