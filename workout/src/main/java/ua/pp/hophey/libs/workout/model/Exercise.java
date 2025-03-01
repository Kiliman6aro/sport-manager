package ua.pp.hophey.libs.workout.model;


import ua.pp.hophey.libs.workout.event.EventBus;
import ua.pp.hophey.libs.workout.event.exercise.ExerciseFinishedEvent;
import ua.pp.hophey.libs.workout.event.exercise.ExerciseStartedEvent;

public class Exercise {

    private final long durationMillis;

    public Exercise(long durationMillis) {
        this.durationMillis = durationMillis;
    }

    public void start() {
        EventBus.getInstance().post(new ExerciseStartedEvent(this, durationMillis));
    }

    public void stop() {
        EventBus.getInstance().post(new ExerciseFinishedEvent(this));
    }

    public long getDurationMillis(){
        return durationMillis;
    }
}