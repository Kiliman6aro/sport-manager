package ua.pp.hophey.libs.workout.event.workout;


import ua.pp.hophey.libs.workout.event.WorkoutEvent;

public class WorkoutStartedEvent extends WorkoutEvent {
    public WorkoutStartedEvent(Object source) {
        super(source);
    }
}