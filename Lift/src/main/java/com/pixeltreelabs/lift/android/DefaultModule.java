package com.pixeltreelabs.lift.android;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.pixeltreelabs.lift.android.ui.ExerciseListFragment;
import com.pixeltreelabs.lift.android.ui.ExerciseSessionFragment;
import com.pixeltreelabs.lift.android.ui.ExerciseSessionListFragment;
import com.pixeltreelabs.lift.android.ui.MainActivity;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mmichihara on 6/19/13.
 */
@Module(entryPoints = {MainActivity.class, ExerciseListFragment.class, ExerciseSessionFragment.class, ExerciseSessionListFragment.class})
public class DefaultModule {
    private final Application mApplication;

    public DefaultModule(Application application) {
        mApplication = application;
    }

    @Provides
    public Context provideApplicationContext() {
        return mApplication;
    }

    @Provides
    public SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    public Bus provideBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new Gson();
    }
}
