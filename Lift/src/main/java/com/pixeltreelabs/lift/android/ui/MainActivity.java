package com.pixeltreelabs.lift.android.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.pixeltreelabs.lift.android.LiftApplication;
import com.pixeltreelabs.lift.android.R;
import com.pixeltreelabs.lift.android.event.ExerciseSelectedEvent;
import com.pixeltreelabs.lift.android.event.ExerciseSessionFinishedEvent;
import com.pixeltreelabs.lift.android.event.NewExerciseClickedEvent;
import com.pixeltreelabs.lift.android.event.NewExerciseSavedEvent;
import com.pixeltreelabs.lift.android.event.ViewMoreSessionsClickedEvent;
import com.pixeltreelabs.lift.android.model.Exercise;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import timber.log.Timber;

public class MainActivity extends Activity {
    @Inject Bus bus;
    @Inject Timber timber;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((LiftApplication) getApplication()).inject(this);

        ExerciseListFragment exerciseListFragment = new ExerciseListFragment();
        getFragmentManager().beginTransaction().add(R.id.fragment_container, exerciseListFragment).commit();
    }

    @Override protected void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override protected void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Subscribe public void onExerciseSelected(ExerciseSelectedEvent event) {
        timber.d("onExerciseSelected called");
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
        transaction.replace(R.id.fragment_container, exerciseSetFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Subscribe public void onExerciseSessionFinished(ExerciseSessionFinishedEvent e) {
        timber.d("onExerciseSessionFinished called");
        getActionBar().setTitle(R.string.app_name);
        getActionBar().setDisplayHomeAsUpEnabled(false);
        getFragmentManager().popBackStack();
    }

    @Subscribe public void onViewMoreSessionsClicked(ViewMoreSessionsClickedEvent e) {
        timber.d("onViewMoreSessionsClicked called");
        Exercise exercise = e.getExercise();
        getActionBar().setTitle(exercise.getName() + " Sessions");
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Construct the bundle to send to the fragment.
        Bundle args = new Bundle();
        args.putParcelable(ExerciseSessionListFragment.ARG_EXERCISE, exercise);

        ExerciseSessionListFragment exerciseSessionListFragment = new ExerciseSessionListFragment();
        exerciseSessionListFragment.setArguments(args);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, exerciseSessionListFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Subscribe public void onNewExerciseClicked(NewExerciseClickedEvent e) {
        timber.d("onNewExerciseClicked called");

        // Remove any currently showing new exercise dialogs.
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("new_exercise_dialog");
        if (prev != null) {
            transaction.remove(prev);
        }
        transaction.addToBackStack(null);

        // Create and show the new dialog.
        NewExerciseDialogFragment newExerciseDialogFragment = new NewExerciseDialogFragment();
        newExerciseDialogFragment.show(getFragmentManager(), "new_exercise_dialog");
    }

    @Subscribe public void onNewExerciseSaved(NewExerciseSavedEvent e) {
        timber.d("onNewExerciseSaved called");
    }
}
