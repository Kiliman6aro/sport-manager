package ua.pp.hophey.pushupapp.workoutlib.model;

import ua.pp.hophey.pushupapp.workoutlib.event.EventType;
import ua.pp.hophey.pushupapp.workoutlib.event.WorkoutListener;

import java.util.ArrayList;
import java.util.List;

public class Set implements Executable {
    private final List<Exercise> exercises;
    private final List<WorkoutListener> listeners = new ArrayList<>();
    private int currentExerciseIndex = 0;

    public Set(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public void addListener(WorkoutListener listener) {
        listeners.add(listener);
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    @Override
    public void start() {
        notifyListeners(EventType.SET_STARTED);
        if (!exercises.isEmpty()) {
            currentExerciseIndex = 0;
            exercises.get(currentExerciseIndex).start();
        }
    }

    @Override
    public void stop() {
        notifyListeners(EventType.SET_FINISHED);
    }

    public void nextExercise() {
        currentExerciseIndex++;
        if (currentExerciseIndex < exercises.size()) {
            exercises.get(currentExerciseIndex).start();
        } else {
            stop();
        }
    }

    private void notifyListeners(EventType eventType) {
        for (WorkoutListener listener : listeners) {
            listener.onEvent(eventType, this);
        }
    }
}