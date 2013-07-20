package com.pixeltreelabs.lift.android.model;

public class ExerciseSet {
    private final int numReps;
    private final int weight;

    public ExerciseSet(int numReps, int weight) {
        this.numReps = numReps;
        this.weight = weight;
    }

    public int getNumReps() {
        return numReps;
    }

    public int getWeight() {
        return weight;
    }

    @Override public String toString() {
        return "Exercise Set = {reps=" + numReps + " weight=" + weight + "}";
    }
}
