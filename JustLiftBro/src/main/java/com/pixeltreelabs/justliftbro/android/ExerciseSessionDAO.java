package com.pixeltreelabs.justliftbro.android;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by mmichihara on 6/19/13.
 */
public class ExerciseSessionDAO {
    private static final String KEY_EXERCISE_SESSIONS = "exercise_sessions";
    private final SharedPreferences mSharedPreferences;
    private final Gson mGson;

    @Inject
    public ExerciseSessionDAO(SharedPreferences sharedPreferences, Gson gson) {
        mSharedPreferences = sharedPreferences;
        mGson = gson;
    }

    public List<ExerciseSession> all() {
        String exerciseSessionsString = mSharedPreferences.getString(KEY_EXERCISE_SESSIONS, null);

        if (exerciseSessionsString == null) {
            return new ArrayList<ExerciseSession>();
        }

        List<ExerciseSession> exerciseSessions = mGson.fromJson(exerciseSessionsString, new TypeToken<List<ExerciseSession>>() {
        }.getType());

        return exerciseSessions;
    }

    public List<ExerciseSession> get(Exercise exercise) {
        List<ExerciseSession> sessions  = new ArrayList<ExerciseSession>();
        for (ExerciseSession session : all()) {
            if (session.getExercise().equals(exercise)) {
                sessions.add(session);
            }
        }
        return sessions;
    }

    public void save(ExerciseSession session) {
        List<ExerciseSession> exerciseSessions = all();
        exerciseSessions.add(0, session);

        String exerciseSessionsString = mGson.toJson(exerciseSessions);
        mSharedPreferences.edit().putString(KEY_EXERCISE_SESSIONS, exerciseSessionsString).apply();
    }
}
