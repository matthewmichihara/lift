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
import com.pixeltreelabs.lift.android.model.Exercise;
import com.pixeltreelabs.lift.android.event.ExerciseSelectedEvent;
import com.pixeltreelabs.lift.android.R;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Views;

public class ExerciseListFragment extends Fragment {
    @Inject Bus bus;

    @InjectView(R.id.exercise_grid) GridView exerciseGrid;

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((LiftApplication) activity.getApplication()).inject(this);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_exercise_list, container, false);
        Views.inject(this, v);

        List<Exercise> exercises = new ArrayList<Exercise>();
        exercises.add(new Exercise("BENCH"));
        exercises.add(new Exercise("SQUAT"));
        exercises.add(new Exercise("DEADLIFT"));

        exerciseGrid.setAdapter(new ExerciseListAdapter(getActivity(), exercises));
        exerciseGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exercise exercise = (Exercise) parent.getItemAtPosition(position);
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
}
