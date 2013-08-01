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
import org.joda.time.Days;

import java.text.NumberFormat;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Views;
import timber.log.Timber;

public class LiftStatsDialogFragment extends DialogFragment {
    @InjectView(R.id.total_weight) TextView totalWeightTextView;
    @InjectView(R.id.over_days) TextView overDaysTextView;
    @InjectView(R.id.random_text) TextView randomTextView;

    @Inject ExerciseSessionStore exerciseSessionStore;
    @Inject Random random;

    private static final int[] BRO_QUOTES = new int[]{R.string.bro_do_u_even_lift, R.string.cool_story_bro, R.string.u_mad_bro, R.string.time_to_get_wheysted};

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((LiftApplication) activity.getApplication()).inject(this);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lift_stats, container, false);
        Views.inject(this, v);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TotalWeightResult result = getTotalWeightOverAllTime();
        totalWeightTextView.setText(getString(R.string.x_pounds, NumberFormat.getIntegerInstance().format(result.getTotalWeight())));
        overDaysTextView.setText(getString(R.string.over_x_days, NumberFormat.getIntegerInstance().format(result.getDaysAgo())));

        int i = random.nextInt(BRO_QUOTES.length);
        randomTextView.setText(BRO_QUOTES[i]);

        return v;
    }

    private TotalWeightResult getTotalWeightOverAllTime() {
        List<ExerciseSession> sessions = exerciseSessionStore.all();

        double totalWeight = 0;
        DateTime oldestDateTime = null;

        for (ExerciseSession session : sessions) {
            DateTime sessionTime = new DateTime(session.getDate());
            if (oldestDateTime == null || sessionTime.isBefore(oldestDateTime)) {
                oldestDateTime = sessionTime;
            }

            for (ExerciseSet set : session.getExerciseSets()) {
                totalWeight += set.getWeight() * set.getNumReps();
            }
        }

        int daysAgo = 0;
        if (oldestDateTime != null) {
            DateTime today = new DateTime();
            daysAgo = Days.daysBetween(oldestDateTime, today).getDays();
        }

        return new TotalWeightResult(daysAgo, totalWeight);
    }

    static class TotalWeightResult {
        private final int daysAgo;
        private final double totalWeight;

        public TotalWeightResult(int daysAgo, double totalWeight) {
            this.daysAgo = daysAgo;
            this.totalWeight = totalWeight;
        }

        public int getDaysAgo() {
            return daysAgo;
        }

        public double getTotalWeight() {
            return totalWeight;
        }
    }
}
