package com.pixeltreelabs.lift.android.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mmichihara on 6/12/13.
 */
public class Exercise implements Parcelable {
    private final String mName;

    public Exercise(String name) {
        mName = name;
    }

    private Exercise(Parcel in) {
        mName = in.readString();
    }

    public String getName() {
        return mName;
    }

    @Override public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof Exercise))
            return false;

        Exercise exercise = (Exercise) other;
        return mName.equals(exercise.getName());
    }

    @Override public int hashCode() {
        int hash = 1;
        hash = hash * 31 + mName.hashCode();
        return hash;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel out, int flags) {
        out.writeString(mName);
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
