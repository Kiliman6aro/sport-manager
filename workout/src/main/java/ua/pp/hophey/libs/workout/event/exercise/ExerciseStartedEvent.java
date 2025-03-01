package ua.pp.hophey.libs.workout.event.exercise;


import ua.pp.hophey.libs.workout.event.WorkoutEvent;

public class ExerciseStartedEvent extends WorkoutEvent {
    private final long durationMillis;

    public ExerciseStartedEvent(Object source, long durationMillis) {
        super(source);
        this.durationMillis = durationMillis;
    }

    public long getDurationMillis() {
        return durationMillis;
    }
}
