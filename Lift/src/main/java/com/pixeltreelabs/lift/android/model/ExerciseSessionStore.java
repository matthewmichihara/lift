package com.pixeltreelabs.lift.android.model;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExerciseSessionStore {
    private static final String KEY_EXERCISE_SESSIONS = "exercise_sessions";

    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    @Inject
    public ExerciseSessionStore(SharedPreferences sharedPreferences, Gson gson) {
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
    }

    public List<ExerciseSession> all() {
        String exerciseSessionsString = sharedPreferences.getString(KEY_EXERCISE_SESSIONS, null);

        if (exerciseSessionsString == null) {
            return new ArrayList<ExerciseSession>();
        }

        TypeToken<List<ExerciseSession>> typeToken = new TypeToken<List<ExerciseSession>>() {
        };
        Type type = typeToken.getType();
        List<ExerciseSession> exerciseSessions = gson.fromJson(exerciseSessionsString, type);

        return exerciseSessions;
    }

    public List<ExerciseSession> get(Exercise exercise) {
        List<ExerciseSession> sessions = new ArrayList<ExerciseSession>();
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

        String exerciseSessionsString = gson.toJson(exerciseSessions);
        sharedPreferences.edit().putString(KEY_EXERCISE_SESSIONS, exerciseSessionsString).apply();
    }
}
