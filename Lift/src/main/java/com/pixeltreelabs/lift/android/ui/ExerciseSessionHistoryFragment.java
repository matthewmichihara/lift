package com.pixeltreelabs.lift.android.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.pixeltreelabs.lift.android.LiftApplication;
import com.pixeltreelabs.lift.android.R;
import com.pixeltreelabs.lift.android.model.ExerciseSession;
import com.pixeltreelabs.lift.android.model.ExerciseSet;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Views;
import timber.log.Timber;

public class ExerciseSessionHistoryFragment extends Fragment {
    public static final String ARG_EXERCISE_SESSION = "exercise_session";

    private ExerciseSession exerciseSession;

    @Inject Timber timber;

    @InjectView(R.id.sets) ListView setsList;
    @InjectView(R.id.comment) TextView commentText;

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((LiftApplication) activity.getApplication()).inject(this);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_session_history, container, false);
        Views.inject(this, view);

        exerciseSession = getArguments().getParcelable(ARG_EXERCISE_SESSION);
        List<ExerciseSet> sets = exerciseSession.getExerciseSets();

        ExerciseSetListAdapter adapter = new ExerciseSetListAdapter(getActivity(), sets);
        setsList.setAdapter(adapter);

        String notes = exerciseSession.getNotes();
        if (TextUtils.isEmpty(notes)) {
            commentText.setText(R.string.na);
        } else {
            commentText.setText(notes);
        }

        return view;
    }

    @Override public void onStart() {
        super.onStart();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String exerciseName = exerciseSession.getExercise().getName();
        String dateString = sdf.format(exerciseSession.getDate());

        getActivity().getActionBar().setTitle(getString(R.string.exercise_date, exerciseName, dateString));
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
