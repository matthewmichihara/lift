package com.pixeltreelabs.lift.android.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pixeltreelabs.lift.android.model.Exercise;
import com.pixeltreelabs.lift.android.R;

import java.util.List;

import butterknife.InjectView;
import butterknife.Views;

public class ExerciseListAdapter extends ArrayAdapter<Exercise> {

    public ExerciseListAdapter(Context context, List<Exercise> exercises) {
        super(context, R.layout.list_item_exercise, exercises);
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_exercise, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Exercise exercise = getItem(position);
        holder.name.setText(exercise.getName());
        holder.weight.setText("20lbs");
        holder.reps.setText("10 REPS");

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.name) TextView name;
        @InjectView(R.id.weight) TextView weight;
        @InjectView(R.id.reps) TextView reps;

        public ViewHolder(View v) {
            Views.inject(ViewHolder.this, v);
        }
    }
}
