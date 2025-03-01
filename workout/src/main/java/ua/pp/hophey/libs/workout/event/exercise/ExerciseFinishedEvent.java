package ua.pp.hophey.libs.workout.event.exercise;


import ua.pp.hophey.libs.workout.event.WorkoutEvent;

public class ExerciseFinishedEvent extends WorkoutEvent {
    public ExerciseFinishedEvent(Object source) {
        super(source);
    }
}
