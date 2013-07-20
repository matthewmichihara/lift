package com.pixeltreelabs.lift.android.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pixeltreelabs.lift.android.model.ExerciseSession;
import com.pixeltreelabs.lift.android.R;

import java.util.List;

import butterknife.InjectView;
import butterknife.Views;

/**
 * Created by mmichihara on 7/18/13.
 */
public class ExerciseSessionListAdapter extends ArrayAdapter<ExerciseSession> {
    public ExerciseSessionListAdapter(Context context, List<ExerciseSession> exerciseSessions) {
        super(context, R.layout.list_item_exercise_session, exerciseSessions);
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_exercise_session, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ExerciseSession exerciseSession = getItem(position);
        holder.tvDate.setText(exerciseSession.getDate().toString());

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_exercise_session_date) TextView tvDate;

        public ViewHolder(View v) {
            Views.inject(ViewHolder.this, v);
        }
    }
}
