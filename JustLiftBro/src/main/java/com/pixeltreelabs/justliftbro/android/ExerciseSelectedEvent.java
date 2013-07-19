package com.pixeltreelabs.justliftbro.android;

/**
 * Created by mmichihara on 6/19/13.
 */
public class ExerciseSelectedEvent {
    private final Exercise mExercise;

    public ExerciseSelectedEvent(Exercise exercise) {
        mExercise = exercise;
    }

    public Exercise getExercise() {
        return mExercise;
    }
}
