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
import com.pixeltreelabs.lift.android.ui.NewExerciseDialogFragment;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

@Module(
        entryPoints = {
                MainActivity.class,
                ExerciseListFragment.class,
                ExerciseSessionFragment.class,
                ExerciseSessionListFragment.class,
                NewExerciseDialogFragment.class
        }
)
public class LiftModule {
    private final Application liftApp;

    public LiftModule(Application application) {
        liftApp = application;
    }

    @Provides public Context provideApplicationContext() {
        return liftApp;
    }

    @Provides public SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides @Singleton public Bus provideBus() {
        return new Bus();
    }

    @Provides @Singleton public Gson provideGson() {
        return new Gson();
    }

    @Provides @Singleton Timber provideTimber() {
        return BuildConfig.DEBUG ? Timber.DEBUG : Timber.PROD;
    }
}
