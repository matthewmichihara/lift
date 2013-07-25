package com.pixeltreelabs.lift.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExerciseSession implements Parcelable {
    private final Exercise exercise;
    private final List<ExerciseSet> exerciseSets;
    private final long date;
    private final String notes;

    public ExerciseSession(Exercise exercise, List<ExerciseSet> exerciseSets, String notes, Date date) {
        this.exercise = exercise;
        this.exerciseSets = exerciseSets;
        this.notes = notes;
        this.date = date.getTime();
    }

    private ExerciseSession(Parcel in) {
        exercise = in.readParcelable(Exercise.class.getClassLoader());
        exerciseSets = new ArrayList<ExerciseSet>();
        in.readTypedList(exerciseSets, ExerciseSet.CREATOR);
        date = in.readLong();
        notes = in.readString();

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
        return new Date(date);
    }

    public int getHeaviestSetWeight() {
        int heaviestWeight = 0;
        for (ExerciseSet set : exerciseSets) {
            if (set.getWeight() > heaviestWeight) {
                heaviestWeight = set.getWeight();
            }
        }

        return heaviestWeight;
    }

    @Override public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(exercise)
                .append(exerciseSets)
                .append(date)
                .append(notes).toHashCode();
    }

    @Override public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        ExerciseSession other = (ExerciseSession) obj;
        return new EqualsBuilder()
                .append(exercise, other.exercise)
                .append(exerciseSets, other.exerciseSets)
                .append(date, other.date)
                .append(notes, other.notes)
                .isEquals();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(exercise, flags);
        out.writeTypedList(exerciseSets);
        out.writeLong(date);
        out.writeString(notes);
    }

    public static final Parcelable.Creator<ExerciseSession> CREATOR = new Parcelable.Creator<ExerciseSession>() {
        public ExerciseSession createFromParcel(Parcel in) {
            return new ExerciseSession(in);
        }

        public ExerciseSession[] newArray(int size) {
            return new ExerciseSession[size];
        }
    };
}
