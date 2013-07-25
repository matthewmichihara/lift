package com.pixeltreelabs.lift.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ExerciseSet implements Parcelable {
    private final int numReps;
    private final int weight;

    public ExerciseSet(int weight, int numReps) {
        this.weight = weight;
        this.numReps = numReps;
    }

    public ExerciseSet(Parcel in) {
        numReps = in.readInt();
        weight = in.readInt();
    }

    public int getNumReps() {
        return numReps;
    }

    public int getWeight() {
        return weight;
    }

    @Override public String toString() {
        return "Exercise Set = {reps=" + numReps + " weight=" + weight + "}";
    }

    @Override public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(numReps)
                .append(weight)
                .toHashCode();
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
        ExerciseSet other = (ExerciseSet) obj;
        return new EqualsBuilder()
                .append(numReps, other.numReps)
                .append(weight, other.weight)
                .isEquals();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel out, int flags) {
        out.writeInt(numReps);
        out.writeInt(weight);
    }

    public static final Parcelable.Creator<ExerciseSet> CREATOR
            = new Parcelable.Creator<ExerciseSet>() {
        public ExerciseSet createFromParcel(Parcel in) {
            return new ExerciseSet(in);
        }

        public ExerciseSet[] newArray(int size) {
            return new ExerciseSet[size];
        }
    };
}
