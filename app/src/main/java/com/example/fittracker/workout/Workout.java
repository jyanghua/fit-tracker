package com.example.fittracker.workout;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;

public class Workout {

    public String name;
    public int sets;
    public String duration;
    public int reps;
    public int weight;
    public String type;


    public Workout ( String name, String type) {
        this.name = name;
        this.type = type;
        this.reps = 0;
        this.weight = 0;
        this.duration = "0 seconds";
    }

    public String getName() {
        return this.name;
    }

    public int getSets() {
        return this.sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getReps() {
        return this.reps;
    }

    public void addRep(int reps) {
        this.reps = reps;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight (int weight) {
        this.weight = weight;
    }

    public String getType() {
        return this.type;
    }
}
