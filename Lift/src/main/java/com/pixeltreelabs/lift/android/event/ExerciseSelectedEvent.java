package com.pixeltreelabs.lift.android.event;

import com.pixeltreelabs.lift.android.model.Exercise;

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
