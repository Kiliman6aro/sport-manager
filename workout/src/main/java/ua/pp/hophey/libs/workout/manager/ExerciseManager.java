package ua.pp.hophey.libs.workout.manager;

import ua.pp.hophey.libs.workout.model.Exercise;

public interface ExerciseManager {
    void start(Exercise exercise);
    void stop();
    boolean isExerciseRunning();
    void cleanup();
}
