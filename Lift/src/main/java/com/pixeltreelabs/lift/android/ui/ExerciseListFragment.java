package com.pixeltreelabs.lift.android.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        final List<Exercise> exercises = exerciseStore.all();
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

        exerciseGrid.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        exerciseGrid.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }

            @Override public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Inflate a menu resource providing context menu items
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context_menu, menu);
                mode.setTitle(R.string.delete_selected_exercises);
                return true;
            }

            @Override public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        // Are you serious?
                        Set<Exercise> exercisesToRemove = new HashSet<Exercise>();

                        SparseBooleanArray checked = exerciseGrid.getCheckedItemPositions();
                        for (int i = 0; i < checked.size(); i++) {
                            if (checked.valueAt(i)) {
                                Exercise exercise = exerciseListAdapter.getItem(checked.keyAt(i));
                                if (exercise != DUMMY_BUTTON_EXERCISE) {
                                    exercisesToRemove.add(exerciseListAdapter.getItem(checked.keyAt(i)));
                                }
                            }
                        }

                        exercises.removeAll(exercisesToRemove);
                        for (Exercise exercise : exercisesToRemove) {
                            exerciseStore.remove(exercise);
                        }

                        exerciseListAdapter.notifyDataSetChanged();

                        mode.finish(); // Action picked, so close the CAB
                        return true;
                    default:
                        return false;
                }
            }

            @Override public void onDestroyActionMode(ActionMode mode) {

            }
        });

        return v;
    }

    @Override public void onStart() {
        super.onStart();
        bus.register(this);

        getActivity().getActionBar().setTitle(R.string.app_name);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);
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
