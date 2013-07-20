package com.pixeltreelabs.lift.android.ui;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pixeltreelabs.lift.android.LiftApplication;
import com.pixeltreelabs.lift.android.R;
import com.pixeltreelabs.lift.android.event.NewExerciseSavedEvent;
import com.pixeltreelabs.lift.android.model.Exercise;
import com.pixeltreelabs.lift.android.model.ExerciseStore;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Views;

public class NewExerciseDialogFragment extends DialogFragment {

    @Inject Bus bus;
    @Inject ExerciseStore exerciseStore;

    @InjectView(R.id.exercise_name) EditText exerciseName;
    @InjectView(R.id.save) Button save;

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((LiftApplication) activity.getApplication()).inject(this);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_exercise, container, false);
        Views.inject(this, v);

        save.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                String name = exerciseName.getText().toString();

                if (TextUtils.isEmpty(name)) return;

                Exercise exercise = new Exercise(name.toUpperCase());
                exerciseStore.save(exercise);
                bus.post(new NewExerciseSavedEvent(exercise));
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
