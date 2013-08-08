package com.pixeltreelabs.lift.android.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pixeltreelabs.lift.android.R;
import com.pixeltreelabs.lift.android.model.ExerciseSet;

import java.util.List;

import butterknife.InjectView;
import butterknife.Views;

public class ExerciseSetListAdapter extends ArrayAdapter<ExerciseSet> {

    public ExerciseSetListAdapter(Context context, List<ExerciseSet> exerciseSets) {
        super(context, R.layout.list_item_exercise_set, exerciseSets);
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_exercise_set, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ExerciseSet set = getItem(position);
        holder.weightText.setText(getContext().getString(R.string.weight_x, set.getWeight()));
        holder.repsText.setText(getContext().getString(R.string.reps_x, set.getNumReps()));

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.weight) TextView weightText;
        @InjectView(R.id.reps) TextView repsText;

        public ViewHolder(View v) {
            Views.inject(ViewHolder.this, v);
        }
    }
}
