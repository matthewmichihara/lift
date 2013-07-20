package com.pixeltreelabs.lift.android.ui;

import android.app.FragmentTransaction;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import com.pixeltreelabs.lift.android.model.Exercise;
import com.pixeltreelabs.lift.android.event.ExerciseSelectedEvent;
import com.pixeltreelabs.lift.android.event.ExerciseSessionFinishedEvent;
import com.pixeltreelabs.lift.android.JustLiftBroApplication;
import com.pixeltreelabs.lift.android.R;
import com.pixeltreelabs.lift.android.event.ViewMoreSessionsClickedEvent;
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

        BitmapDrawable actionBarBackground = new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.bg_action_bar_tile));
        actionBarBackground.setTileModeX(Shader.TileMode.REPEAT);
        getActionBar().setBackgroundDrawable(actionBarBackground);

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