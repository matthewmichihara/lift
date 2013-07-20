package com.pixeltreelabs.lift.android.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pixeltreelabs.lift.android.LiftApplication;
import com.pixeltreelabs.lift.android.R;
import com.pixeltreelabs.lift.android.event.ExerciseSessionFinishedEvent;
import com.pixeltreelabs.lift.android.event.ViewMoreSessionsClickedEvent;
import com.pixeltreelabs.lift.android.model.Exercise;
import com.pixeltreelabs.lift.android.model.ExerciseSession;
import com.pixeltreelabs.lift.android.model.ExerciseSessionStore;
import com.pixeltreelabs.lift.android.model.ExerciseSet;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Views;
import timber.log.Timber;

/**
 * Created by mmichihara on 6/19/13.
 */
public class ExerciseSessionFragment extends Fragment {
    public static final String ARG_EXERCISE = "exercise";

    private Exercise exercise;

    @Inject Bus bus;
    @Inject Timber timber;
    @Inject ExerciseSessionStore exerciseSessionStore;

    @InjectView(R.id.new_set_list) LinearLayout newSetList;
    @InjectView(R.id.new_set) Button newSet;
    @InjectView(R.id.complete_session) Button completeSession;
    @InjectView(R.id.new_notes) EditText newNotes;
    @InjectView(R.id.last_set_list) LinearLayout lastSetList;
    @InjectView(R.id.last_notes) TextView lastNotes;
    @InjectView(R.id.more_sessions) Button moreSessions;

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((LiftApplication) activity.getApplication()).inject(this);
    }

    @Override public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_exercise_set, container, false);
        Views.inject(this, view);

        exercise = getArguments().getParcelable(ARG_EXERCISE);
        if (exercise == null) throw new RuntimeException();

        // Add the initial row.
        View exerciseSetRow = inflater.inflate(R.layout.set_item, container, false);
        newSetList.addView(exerciseSetRow);

        newSet.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                View exerciseSetRow = inflater.inflate(R.layout.set_item, container, false);
                newSetList.addView(exerciseSetRow);
            }
        });

        completeSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ExerciseSet> sets = new ArrayList<ExerciseSet>();

                int childCount = newSetList.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    LinearLayout setLinearLayout = (LinearLayout) newSetList.getChildAt(i);
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
                    timber.d("New exercise set created: %s", set);
                    sets.add(set);
                }

                String notes = newNotes.getText().toString();

                ExerciseSession session = new ExerciseSession(exercise, sets, notes, new Date());

                timber.d("Saving exercise session: %s", session);
                exerciseSessionStore.save(session);

                bus.post(new ExerciseSessionFinishedEvent());
            }
        });

        moreSessions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bus.post(new ViewMoreSessionsClickedEvent(exercise));
            }
        });

        // Populate last set from DAO.
        if (exercise == null) throw new RuntimeException();
        List<ExerciseSession> sessions = exerciseSessionStore.get(exercise);
        if (!sessions.isEmpty()) {
            ExerciseSession lastSession = sessions.get(0);
            List<ExerciseSet> sets = lastSession.getExerciseSets();
            for (ExerciseSet set : sets) {
                View setRow = inflater.inflate(R.layout.set_item, container, false);

                EditText weightEditText = (EditText) setRow.findViewById(R.id.weight);
                EditText repsEditText = (EditText) setRow.findViewById(R.id.reps);

                weightEditText.setText(String.valueOf(set.getWeight()));
                repsEditText.setText(String.valueOf(set.getNumReps()));

                lastSetList.addView(setRow);
            }

            String notes = lastSession.getNotes();
            lastNotes.setText(notes);
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        bus.unregister(this);
    }
}
