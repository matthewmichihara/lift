package com.pixeltreelabs.lift.android.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pixeltreelabs.lift.android.model.Exercise;
import com.pixeltreelabs.lift.android.model.ExerciseSession;
import com.pixeltreelabs.lift.android.model.ExerciseSessionDAO;
import com.pixeltreelabs.lift.android.event.ExerciseSessionFinishedEvent;
import com.pixeltreelabs.lift.android.model.ExerciseSet;
import com.pixeltreelabs.lift.android.JustLiftBroApplication;
import com.pixeltreelabs.lift.android.R;
import com.pixeltreelabs.lift.android.event.ViewMoreSessionsClickedEvent;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Views;

/**
 * Created by mmichihara on 6/19/13.
 */
public class ExerciseSessionFragment extends Fragment {
    public static final String ARG_EXERCISE = "exercise";

    private Exercise mExercise;

    @Inject Bus mBus;
    @Inject ExerciseSessionDAO mExerciseSessionDAO;

    @InjectView(R.id.btn_new_set) Button mBtnNewSet;
    @InjectView(R.id.btn_complete_set) Button mBtnCompleteSet;
    @InjectView(R.id.et_new_notes) EditText mEtNewNotes;
    @InjectView(R.id.tv_last_notes) TextView mEtLastNotes;
    @InjectView(R.id.ll_new_set) LinearLayout mLlNewSet;
    @InjectView(R.id.ll_last_set) LinearLayout mLlLastSet;
    @InjectView(R.id.btn_view_more_sessions) Button mBtnViewMoreSessions;

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((JustLiftBroApplication) activity.getApplication()).inject(this);
    }

    @Override public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_exercise_set, container, false);
        Views.inject(this, view);

        mExercise = getArguments().getParcelable(ARG_EXERCISE);
        if (mExercise == null) {
            throw new RuntimeException();
        }

        // Add the initial row.
        View exerciseSetRow = inflater.inflate(R.layout.set_item, container, false);
        mLlNewSet.addView(exerciseSetRow);

        mBtnNewSet.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                View exerciseSetRow = inflater.inflate(R.layout.set_item, container, false);
                mLlNewSet.addView(exerciseSetRow);
            }
        });

        mBtnCompleteSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ExerciseSet> sets = new ArrayList<ExerciseSet>();

                int childCount = mLlNewSet.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    LinearLayout setLinearLayout = (LinearLayout) mLlNewSet.getChildAt(i);
                    EditText weightEditText = (EditText) setLinearLayout.getChildAt(0);
                    EditText repsEditText = (EditText) setLinearLayout.getChildAt(1);

                    String weightString = weightEditText.getText().toString();
                    String repsString = repsEditText.getText().toString();

                    if (TextUtils.isEmpty(weightString) || TextUtils.isEmpty(repsString)) {
                        continue;
                    }

                    int weight = Integer.parseInt(weightEditText.getText().toString());
                    int height = Integer.parseInt(repsEditText.getText().toString());

                    ExerciseSet set = new ExerciseSet(weight, height);
                    Log.d("ExerciseSessionFragment", set.toString());
                    sets.add(set);
                }

                String notes = mEtNewNotes.getText().toString();

                ExerciseSession session = new ExerciseSession(mExercise, sets, notes, new Date());
                mExerciseSessionDAO.save(session);

                mBus.post(new ExerciseSessionFinishedEvent());
            }
        });

        mBtnViewMoreSessions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBus.post(new ViewMoreSessionsClickedEvent(mExercise));
            }
        });

        // Populate last set from DAO.
        if (mExercise == null) throw new RuntimeException();
        List<ExerciseSession> sessions = mExerciseSessionDAO.get(mExercise);
        if (!sessions.isEmpty()) {
            ExerciseSession lastSession = sessions.get(0);
            List<ExerciseSet> sets = lastSession.getExerciseSets();
            for (ExerciseSet set : sets) {
                View setRow = inflater.inflate(R.layout.set_item, container, false);

                EditText weightEditText = (EditText) setRow.findViewById(R.id.et_weight);
                EditText repsEditText = (EditText) setRow.findViewById(R.id.et_reps);

                weightEditText.setText(String.valueOf(set.getWeight()));
                repsEditText.setText(String.valueOf(set.getNumReps()));

                mLlLastSet.addView(setRow);
            }

            String notes = lastSession.getNotes();
            mEtLastNotes.setText(notes);
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mBus.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        mBus.unregister(this);
    }
}
