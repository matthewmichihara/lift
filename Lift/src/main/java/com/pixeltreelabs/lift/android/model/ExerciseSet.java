package com.pixeltreelabs.lift.android.model;

public class ExerciseSet {
    private final int numReps;
    private final int weight;

    public ExerciseSet(int weight, int numReps) {
        this.weight = weight;
        this.numReps = numReps;
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
