package com.pixeltreelabs.justliftbro.android;

/**
 * Created by mmichihara on 6/12/13.
 */
public class ExerciseSet {
    private final int mNumReps;
    private final int mWeight;

    public ExerciseSet(int numReps, int weight) {
        mNumReps = numReps;
        mWeight = weight;
    }

    public int getNumReps() {
        return mNumReps;
    }

    public int getWeight() {
        return mWeight;
    }

    @Override
    public String toString() {
        return "Exercise Set = {reps=" + mNumReps + " weight=" + mWeight + "}";
    }
}
