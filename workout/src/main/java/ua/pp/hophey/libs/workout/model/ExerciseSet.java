package ua.pp.hophey.libs.workout.model;


import java.util.List;
import ua.pp.hophey.libs.workout.event.EventBus;
import ua.pp.hophey.libs.workout.event.sets.SetStartedEvent;
import ua.pp.hophey.libs.workout.event.sets.SetFinishedEvent;
import ua.pp.hophey.libs.workout.event.exercise.ExerciseTickEvent;

public class ExerciseSet {
    private final List<Exercise> exercises;

    public ExerciseSet(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public void start() {
        EventBus.getInstance().post(new SetStartedEvent(this));
        for (Exercise exercise : exercises) {
            long remaining = exercise.getDurationMillis();
            while (remaining >= 0) {
                EventBus.getInstance().post(new ExerciseTickEvent(exercise, remaining));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                remaining -= 1000;
            }
            exercise.start();
            remaining = exercise.getDurationMillis();
            while (remaining >= 0) {
                EventBus.getInstance().post(new ExerciseTickEvent(exercise, remaining));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                remaining -= 1000;
            }
            exercise.stop();
        }
        EventBus.getInstance().post(new SetFinishedEvent(this));
    }
}
