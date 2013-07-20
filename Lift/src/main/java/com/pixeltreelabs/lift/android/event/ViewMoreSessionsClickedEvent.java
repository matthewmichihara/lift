package com.pixeltreelabs.lift.android.event;

import com.pixeltreelabs.lift.android.model.Exercise;

public class ViewMoreSessionsClickedEvent {
    private final Exercise exercise;

    public ViewMoreSessionsClickedEvent(Exercise exercise) {
        this.exercise = exercise;
    }

    public Exercise getExercise() {
        return exercise;
    }
}
