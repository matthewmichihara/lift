package com.pixeltreelabs.lift.android.model;

import java.util.Date;
import java.util.List;

public class ExerciseSession {
    private final Exercise exercise;
    private final List<ExerciseSet> exerciseSets;
    private final Date date;
    private final String notes;

    public ExerciseSession(Exercise exercise, List<ExerciseSet> exerciseSets, String notes, Date date) {
        this.exercise = exercise;
        this.exerciseSets = exerciseSets;
        this.notes = notes;
        this.date = date;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public List<ExerciseSet> getExerciseSets() {
        return exerciseSets;
    }

    public String getNotes() {
        return notes;
    }

    public Date getDate() {
        return date;
    }
}
