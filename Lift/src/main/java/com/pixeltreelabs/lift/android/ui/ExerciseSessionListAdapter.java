package com.pixeltreelabs.lift.android.ui;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pixeltreelabs.lift.android.R;
import com.pixeltreelabs.lift.android.model.ExerciseSession;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.InjectView;
import butterknife.Views;

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

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String dateString = sdf.format(exerciseSession.getDate());
        holder.date.setText(Html.fromHtml(getContext().getString(R.string.date_weight, dateString, exerciseSession.getHeaviestSetWeight())));

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.date_weight) TextView date;

        public ViewHolder(View v) {
            Views.inject(ViewHolder.this, v);
        }
    }
}
