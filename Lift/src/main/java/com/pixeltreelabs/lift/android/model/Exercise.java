package com.pixeltreelabs.lift.android.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Exercise implements Parcelable {
    private final String name;

    public Exercise(String name) {
        this.name = name;
    }

    private Exercise(Parcel in) {
        name = in.readString();
    }

    public String getName() {
        return name;
    }

    @Override public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof Exercise))
            return false;

        Exercise exercise = (Exercise) other;
        return name.equals(exercise.getName());
    }

    @Override public int hashCode() {
        int hash = 1;
        hash = hash * 31 + name.hashCode();
        return hash;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
    }

    public static final Parcelable.Creator<Exercise> CREATOR
            = new Parcelable.Creator<Exercise>() {
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

}
