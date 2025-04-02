package ua.pp.hophey.libs.workout.manager.impl;

import ua.pp.hophey.libs.workout.model.Exercise;
import ua.pp.hophey.libs.workout.manager.ExerciseManager;


public class BaseExerciseManager implements ExerciseManager {

    private Thread backgroundThread;
    private volatile boolean isRunning = false;

    @Override
    public void start(Exercise exercise) {
        if (!isRunning) {
            backgroundThread = new Thread(() -> {
                isRunning = true;
                try {
                    exercise.run();
                } finally {
                    isRunning = false;
                }
            });
            backgroundThread.setDaemon(true);
            backgroundThread.start();
        }
    }

    @Override
    public void stop() {
        isRunning = false;
        if (backgroundThread != null) {
            backgroundThread.interrupt();
        }
    }

    @Override
    public boolean isExerciseRunning() {
        return isRunning && (backgroundThread != null && backgroundThread.isAlive());
    }

    @Override
    public void cleanup() {
        stop();
    }
}