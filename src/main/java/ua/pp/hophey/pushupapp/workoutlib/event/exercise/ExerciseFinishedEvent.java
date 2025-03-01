package ua.pp.hophey.pushupapp.workoutlib.event.exercise;

import ua.pp.hophey.pushupapp.workoutlib.event.WorkoutEvent;

public class ExerciseFinishedEvent extends WorkoutEvent {
    public ExerciseFinishedEvent(Object source) {
        super(source);
    }
}
