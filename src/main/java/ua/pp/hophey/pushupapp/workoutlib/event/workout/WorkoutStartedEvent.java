package ua.pp.hophey.pushupapp.workoutlib.event.workout;

import ua.pp.hophey.pushupapp.workoutlib.event.WorkoutEvent;

public class WorkoutStartedEvent extends WorkoutEvent {
    public WorkoutStartedEvent(Object source) {
        super(source);
    }
}