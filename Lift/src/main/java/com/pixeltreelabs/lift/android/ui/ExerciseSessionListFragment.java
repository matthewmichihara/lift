package com.pixeltreelabs.lift.android.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pixeltreelabs.lift.android.LiftApplication;
import com.pixeltreelabs.lift.android.R;
import com.pixeltreelabs.lift.android.event.ExerciseSessionSelectedEvent;
import com.pixeltreelabs.lift.android.model.Exercise;
import com.pixeltreelabs.lift.android.model.ExerciseSession;
import com.pixeltreelabs.lift.android.model.ExerciseSessionStore;
import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Views;

public class ExerciseSessionListFragment extends Fragment {
    public static final String ARG_EXERCISE = "exercise";

    private Exercise exercise;

    @Inject Bus bus;
    @Inject ExerciseSessionStore exerciseSessionStore;

    @InjectView(R.id.exercise_sessions) ListView exerciseSessionList;

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((LiftApplication) activity.getApplication()).inject(this);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_exercise_session_list, container, false);
        Views.inject(this, v);

        exercise = getArguments().getParcelable(ARG_EXERCISE);
        if (exercise == null) throw new RuntimeException();

        List<ExerciseSession> exerciseSessions = exerciseSessionStore.get(exercise);
        exerciseSessionList.setAdapter(new ExerciseSessionListAdapter(getActivity(), exerciseSessions));
        exerciseSessionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExerciseSession exerciseSession = (ExerciseSession) parent.getItemAtPosition(position);
                bus.post(new ExerciseSessionSelectedEvent(exerciseSession));
            }
        });

        return v;
    }

    @Override public void onStart() {
        super.onStart();
        bus.register(this);

        getActivity().getActionBar().setTitle(getString(R.string.x_history, exercise.getName()));
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override public void onStop() {
        super.onStop();
        bus.unregister(this);
    }
}
