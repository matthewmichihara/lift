package com.pixeltreelabs.justliftbro.android;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Views;

public class ExerciseListFragment extends Fragment {
    @Inject Bus mBus;

    @InjectView(R.id.lv_exercises) ListView mLvExercises;

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((JustLiftBroApplication) activity.getApplication()).inject(this);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_exercise_list, container, false);
        Views.inject(this, v);

        List<Exercise> exercises = new ArrayList<Exercise>();
        exercises.add(new Exercise("Bench"));
        exercises.add(new Exercise("Squat"));
        exercises.add(new Exercise("Deadlift"));

        mLvExercises.setAdapter(new ExerciseListAdapter(getActivity(), exercises));
        mLvExercises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exercise exercise = (Exercise) parent.getItemAtPosition(position);
                mBus.post(new ExerciseSelectedEvent(exercise));
            }
        });

        return v;
    }

    @Override public void onStart() {
        super.onStart();
        mBus.register(this);
    }

    @Override public void onStop() {
        super.onStop();
        mBus.unregister(this);
    }
}
