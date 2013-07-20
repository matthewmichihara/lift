package com.pixeltreelabs.lift.android.event;

import com.pixeltreelabs.lift.android.model.Exercise;

public class ExerciseSelectedEvent {
    private final Exercise exercise;

    public ExerciseSelectedEvent(Exercise exercise) {
        this.exercise = exercise;
    }

    public Exercise getExercise() {
        return exercise;
    }
}
