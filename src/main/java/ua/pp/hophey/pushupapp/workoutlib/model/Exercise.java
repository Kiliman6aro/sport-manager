package ua.pp.hophey.pushupapp.workoutlib.model;

import ua.pp.hophey.pushupapp.workoutlib.event.EventBus;
import ua.pp.hophey.pushupapp.workoutlib.event.ExerciseFinishedEvent;
import ua.pp.hophey.pushupapp.workoutlib.event.ExerciseStartedEvent;
import ua.pp.hophey.pushupapp.workoutlib.event.WorkoutEvent;

public class Exercise implements Executable {

    private final long durationMillis;

    public Exercise(long durationMillis) {
        this.durationMillis = durationMillis;
    }

    @Override
    public void start() {
//        System.out.println("Упражнение началось");
//        EventBus.getInstance().post("Упражнение началось");
        EventBus.getInstance().post(new ExerciseStartedEvent(this, durationMillis));
    }

    @Override
    public void stop() {
//        System.out.println("Упражнение коничлось");
//        EventBus.getInstance().post("Упражнение коничлось");
        EventBus.getInstance().post(new ExerciseFinishedEvent(this));
    }

    public long getDurationMillis(){
        return durationMillis;
    }
}