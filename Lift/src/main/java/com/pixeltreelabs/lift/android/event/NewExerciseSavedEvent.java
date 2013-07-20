package com.pixeltreelabs.lift.android.event;

import com.pixeltreelabs.lift.android.model.Exercise;

public class NewExerciseSavedEvent {
    private final Exercise exercise;

    public NewExerciseSavedEvent(Exercise exercise) {
        this.exercise = exercise;
    }

    public Exercise getExercise() {
        return exercise;
    }
}
