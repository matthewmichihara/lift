package com.pixeltreelabs.justliftbro.android;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.InjectView;
import butterknife.Views;

/**
 * Created by mmichihara on 6/19/13.
 */
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
        holder.tvName.setText(exercise.getName());
        holder.tvWeight.setText("20lbs");
        holder.tvReps.setText("10 REPS");

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tvName) TextView tvName;
        @InjectView(R.id.tvWeight) TextView tvWeight;
        @InjectView(R.id.tvReps) TextView tvReps;

        public ViewHolder(View v) {
            Views.inject(ViewHolder.this, v);
        }
     }
}
