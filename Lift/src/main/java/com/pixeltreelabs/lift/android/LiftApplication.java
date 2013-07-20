package com.pixeltreelabs.lift.android;

import android.app.Application;

import dagger.ObjectGraph;

public class LiftApplication extends Application {
    private ObjectGraph mObjectGraph;

    @Override public void onCreate() {
        super.onCreate();
        mObjectGraph = ObjectGraph.create(new LiftModule(this));
    }

    public void inject(Object object) {
        mObjectGraph.inject(object);
    }
}
