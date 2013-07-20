package com.pixeltreelabs.lift.android.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pixeltreelabs.lift.android.model.Exercise;
import com.pixeltreelabs.lift.android.event.ExerciseSelectedEvent;
import com.pixeltreelabs.lift.android.model.ExerciseSession;
import com.pixeltreelabs.lift.android.model.ExerciseSessionDAO;
import com.pixeltreelabs.lift.android.JustLiftBroApplication;
import com.pixeltreelabs.lift.android.R;
import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Views;

/**
 * Created by mmichihara on 7/18/13.
 */
public class ExerciseSessionListFragment extends Fragment {
    public static final String ARG_EXERCISE = "exercise";

    private Exercise mExercise;

    @Inject Bus mBus;
    @Inject ExerciseSessionDAO mExerciseSessionDAO;

    @InjectView(R.id.lv_exercise_sessions) ListView mLvExerciseSessions;

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((JustLiftBroApplication) activity.getApplication()).inject(this);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_exercise_session_list, container, false);
        Views.inject(this, v);

        mExercise = getArguments().getParcelable(ARG_EXERCISE);
        if (mExercise == null) {
            throw new RuntimeException();
        }

        List<ExerciseSession> exerciseSessions = mExerciseSessionDAO.get(mExercise);
        mLvExerciseSessions.setAdapter(new ExerciseSessionListAdapter(getActivity(), exerciseSessions));
        mLvExerciseSessions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExerciseSession exerciseSession = (ExerciseSession) parent.getItemAtPosition(position);
                mBus.post(new ExerciseSelectedEvent(exerciseSession.getExercise()));
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
