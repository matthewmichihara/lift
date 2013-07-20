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
public class ExerciseStore {
    private static final String KEY_EXERCISES = "exercises";

    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    @Inject
    public ExerciseStore(SharedPreferences sharedPreferences, Gson gson) {
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
    }

    public List<Exercise> all() {
        String exercisesString = sharedPreferences.getString(KEY_EXERCISES, null);

        if (exercisesString == null) return new ArrayList<Exercise>();

        TypeToken<List<Exercise>> typeToken = new TypeToken<List<Exercise>>() {
        };
        Type type = typeToken.getType();
        List<Exercise> exercises = gson.fromJson(exercisesString, type);

        return exercises;
    }

    public void save(Exercise exercise) {
        List<Exercise> exercises = all();
        exercises.add(0, exercise);

        String exercisesString = gson.toJson(exercises);
        sharedPreferences.edit().putString(KEY_EXERCISES, exercisesString).apply();
    }
}
