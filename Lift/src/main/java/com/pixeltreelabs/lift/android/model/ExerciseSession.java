package com.pixeltreelabs.lift.android.model;

import java.util.Date;
import java.util.List;

/**
 * Created by mmichihara on 6/12/13.
 */
public class ExerciseSession {
    private final Exercise mExercise;
    private final List<ExerciseSet> mExerciseSets;
    private final Date mDate;
    private final String mNotes;

    public ExerciseSession(Exercise exercise, List<ExerciseSet> exerciseSets, String notes, Date date) {
        mExercise = exercise;
        mExerciseSets = exerciseSets;
        mNotes = notes;
        mDate = date;
    }

    public Exercise getExercise() {
        return mExercise;
    }

    public List<ExerciseSet> getExerciseSets() {
        return mExerciseSets;
    }

    public String getNotes() {
        return mNotes;
    }

    public Date getDate() {
        return mDate;
    }
}
