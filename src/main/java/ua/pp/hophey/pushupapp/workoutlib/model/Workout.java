package ua.pp.hophey.pushupapp.workoutlib.model;

import ua.pp.hophey.pushupapp.workoutlib.event.*;
import ua.pp.hophey.pushupapp.workoutlib.event.workout.RestTickEvent;
import ua.pp.hophey.pushupapp.workoutlib.event.workout.WorkoutFinishedEvent;
import ua.pp.hophey.pushupapp.workoutlib.event.workout.WorkoutStartedEvent;

import java.util.List;

public class Workout{
    private final List<ExerciseSet> sets;

    public Workout(List<ExerciseSet> sets) {
        this.sets = sets;
    }

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
}