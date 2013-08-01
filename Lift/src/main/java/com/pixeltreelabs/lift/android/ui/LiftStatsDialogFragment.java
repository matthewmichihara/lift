package com.pixeltreelabs.lift.android.ui;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.pixeltreelabs.lift.android.LiftApplication;
import com.pixeltreelabs.lift.android.R;
import com.pixeltreelabs.lift.android.model.ExerciseSession;
import com.pixeltreelabs.lift.android.model.ExerciseSessionStore;
import com.pixeltreelabs.lift.android.model.ExerciseSet;

import org.joda.time.DateTime;

import java.text.NumberFormat;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Views;
import timber.log.Timber;

public class LiftStatsDialogFragment extends DialogFragment {
    @InjectView(R.id.total_weight) TextView totalWeightTextView;
    @InjectView(R.id.random_text) TextView randomTextView;

    @Inject ExerciseSessionStore exerciseSessionStore;
    @Inject Timber timber;

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((LiftApplication) activity.getApplication()).inject(this);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lift_stats, container, false);
        Views.inject(this, v);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        int totalWeight = getTotalWeightOverPastWeek();
        totalWeightTextView.setText(getString(R.string.x_pounds, NumberFormat.getIntegerInstance().format(totalWeight)));
        randomTextView.setText("Bro do u even lift?");

        return v;
    }

    private int getTotalWeightOverPastWeek() {
        DateTime weekAgoDateTime = new DateTime().minusWeeks(1);

        List<ExerciseSession> sessions = exerciseSessionStore.all();

        int totalWeight = 0;
        for (ExerciseSession session : sessions) {
            DateTime sessionDateTime = new DateTime(session.getDate());
            timber.d("About to check %s", session);
            if (sessionDateTime.isAfter(weekAgoDateTime)) {
                timber.d("Session date is after week ago");
                for (ExerciseSet set : session.getExerciseSets()) {
                    totalWeight += set.getWeight() * set.getNumReps();
                }
            }
        }

        return totalWeight;
    }
}
