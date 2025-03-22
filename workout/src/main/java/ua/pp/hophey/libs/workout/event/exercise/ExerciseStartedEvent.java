package ua.pp.hophey.libs.workout.event.exercise;


import ua.pp.hophey.libs.workout.event.WorkoutEvent;

public class ExerciseStartedEvent extends WorkoutEvent {

    public ExerciseStartedEvent(Object source) {
        super(source);
    }
}
