package ua.pp.hophey.pushupapp.workoutlib.model;

import ua.pp.hophey.pushupapp.workoutlib.event.*;

import java.util.List;

public class Workout implements Executable{
    private final List<ExerciseSet> sets;

    public Workout(List<ExerciseSet> sets) {
        this.sets = sets;
    }

    @Override
    public void start() {
        EventBus.getInstance().post(new WorkoutStartedEvent(this));
        for (ExerciseSet set : sets) {
            set.start();
            if (sets.indexOf(set) < sets.size() - 1) {
                long remaining = 5000;
                while (remaining >= 0) {
                    EventBus.getInstance().post(new RestTickEvent(this, remaining));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    remaining -= 1000;
                }
            }
        }
        EventBus.getInstance().post(new WorkoutFinishedEvent(this));
    }

    @Override
    public void stop() {

    }
}