package com.pixeltreelabs.justliftbro.android;

/**
 * Created by mmichihara on 7/18/13.
 */
public class ViewMoreSessionsClickedEvent {
    private final Exercise mExercise;

    public ViewMoreSessionsClickedEvent(Exercise exercise) {
        mExercise = exercise;
    }

    public Exercise getExercise() {
        return mExercise;
    }
}
