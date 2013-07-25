package com.pixeltreelabs.lift.android.event;

import com.pixeltreelabs.lift.android.model.ExerciseSession;

public class ExerciseSessionSelectedEvent {
    private final ExerciseSession exerciseSession;

    public ExerciseSessionSelectedEvent(ExerciseSession exerciseSession) {
        this.exerciseSession = exerciseSession;
    }

    public ExerciseSession getExerciseSession() {
        return exerciseSession;
    }
}
