package ua.pp.hophey.pushupapp.workoutlib.model;

import ua.pp.hophey.pushupapp.workoutlib.event.EventBus;
import ua.pp.hophey.pushupapp.workoutlib.event.ExerciseFinishedEvent;
import ua.pp.hophey.pushupapp.workoutlib.event.ExerciseStartedEvent;

public class Exercise implements Executable {

    private final long durationMillis;

    public Exercise(long durationMillis) {
        this.durationMillis = durationMillis;
    }

    @Override
    public void start() {
        EventBus.getInstance().post(new ExerciseStartedEvent(this, durationMillis));
    }

    @Override
    public void stop() {
        EventBus.getInstance().post(new ExerciseFinishedEvent(this));
    }

    public long getDurationMillis(){
        return durationMillis;
    }
}