package com.pixeltreelabs.lift.android;

import android.app.Application;

import dagger.ObjectGraph;

/**
 * Created by mmichihara on 6/19/13.
 */
public class JustLiftBroApplication extends Application {
    private ObjectGraph mObjectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        mObjectGraph = ObjectGraph.create(new DefaultModule(this));
    }

    public void inject(Object object) {
        mObjectGraph.inject(object);
    }
}
