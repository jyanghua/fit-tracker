package com.example.fittracker.workout;

import java.util.ArrayList;


public class Workout {

    public String name;
    public int sets;
    public String duration;
    public ArrayList<Integer> reps;
    public int weight;
    public String type;


    public Workout ( String name, String type) {
        this.name = name;
        this.type = type;
        this.reps = new ArrayList<Integer>();
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

    public void addReps(int rep) {
        this.reps.add(rep);
    }

    public void addAllReps(String reps) {
        if ( reps.length() == 0 ) {
            return;
        }

        for ( String rep : reps.split(",")) {
            this.addReps(Integer.decode(rep));
        }
    }

    public void clearReps() {
        this.reps.clear();
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
