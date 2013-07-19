package com.pixeltreelabs.justliftbro.android;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject Bus mBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((JustLiftBroApplication) getApplication()).inject(this);

        ExerciseListFragment exerciseListFragment = new ExerciseListFragment();
        getFragmentManager().beginTransaction().add(R.id.fl_fragment_container, exerciseListFragment).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBus.unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Subscribe
    public void onExerciseSelected(ExerciseSelectedEvent event) {
        Exercise exercise = event.getExercise();

        // Update the action bar.
        getActionBar().setTitle(exercise.getName());
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Construct the bundle to send to the fragment.
        Bundle args = new Bundle();
        args.putParcelable(ExerciseSessionFragment.ARG_EXERCISE, exercise);

        ExerciseSessionFragment exerciseSetFragment = new ExerciseSessionFragment();
        exerciseSetFragment.setArguments(args);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_fragment_container, exerciseSetFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Subscribe
    public void onExerciseSessionFinished(ExerciseSessionFinishedEvent e) {
        getActionBar().setTitle(R.string.app_name);
        getActionBar().setDisplayHomeAsUpEnabled(false);
        getFragmentManager().popBackStack();
    }

    @Subscribe
    public void onViewMoreSessionsClicked(ViewMoreSessionsClickedEvent e) {
        Exercise exercise = e.getExercise();
        getActionBar().setTitle(exercise.getName() + " Sessions");
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Construct the bundle to send to the fragment.
        Bundle args = new Bundle();
        args.putParcelable(ExerciseSessionListFragment.ARG_EXERCISE, exercise);

        ExerciseSessionListFragment exerciseSessionListFragment = new ExerciseSessionListFragment();
        exerciseSessionListFragment.setArguments(args);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_fragment_container, exerciseSessionListFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
