package com.pixeltreelabs.lift.android.event;

import com.pixeltreelabs.lift.android.model.Exercise;

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
