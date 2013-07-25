package com.pixeltreelabs.lift.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

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
        Exercise other = (Exercise) obj;
        return new EqualsBuilder()
                .append(name, other.name)
                .isEquals();
    }

    @Override public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .toHashCode();
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
