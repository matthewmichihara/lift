package com.pixeltreelabs.lift.android.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.pixeltreelabs.lift.android.LiftApplication;
import com.pixeltreelabs.lift.android.R;
import com.pixeltreelabs.lift.android.event.ExerciseSelectedEvent;
import com.pixeltreelabs.lift.android.event.NewExerciseClickedEvent;
import com.pixeltreelabs.lift.android.event.NewExerciseSavedEvent;
import com.pixeltreelabs.lift.android.model.DummyButtonExercise;
import com.pixeltreelabs.lift.android.model.Exercise;
import com.pixeltreelabs.lift.android.model.ExerciseSessionStore;
import com.pixeltreelabs.lift.android.model.ExerciseStore;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Views;
import timber.log.Timber;

public class ExerciseListFragment extends Fragment {
    @Inject Bus bus;
    @Inject Timber timber;
    @Inject ExerciseStore exerciseStore;
    @Inject ExerciseSessionStore exerciseSessionStore;
    private ExerciseListAdapter exerciseListAdapter;

    @InjectView(R.id.exercise_grid) GridView exerciseGrid;

    private static final DummyButtonExercise DUMMY_BUTTON_EXERCISE = new DummyButtonExercise();

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((LiftApplication) activity.getApplication()).inject(this);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_exercise_list, container, false);
        Views.inject(this, v);

        List<Exercise> exercises = exerciseStore.all();
        exercises.add(DUMMY_BUTTON_EXERCISE);
        exerciseListAdapter = new ExerciseListAdapter(getActivity(), exercises, exerciseSessionStore);
        exerciseGrid.setAdapter(exerciseListAdapter);
        exerciseGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exercise exercise = (Exercise) parent.getItemAtPosition(position);

                if (exercise == DUMMY_BUTTON_EXERCISE) {
                    bus.post(new NewExerciseClickedEvent());
                    return;
                }

                bus.post(new ExerciseSelectedEvent(exercise));
            }
        });

        return v;
    }

    @Override public void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override public void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Subscribe public void onNewExerciseSaved(NewExerciseSavedEvent e) {
        List<Exercise> exercises = exerciseStore.all();
        timber.d("Exercises --");
        for (Exercise exercise : exercises) {
            timber.d("Exercise: %s", exercise);
        }

        exercises.add(DUMMY_BUTTON_EXERCISE);

        exerciseListAdapter.clear();
        exerciseListAdapter.addAll(exercises);
        exerciseListAdapter.notifyDataSetChanged();
    }
}
