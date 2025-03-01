package ua.pp.hophey.libs.workout.event.exercise;


import ua.pp.hophey.libs.workout.event.WorkoutEvent;

public class ExerciseTickEvent extends WorkoutEvent {
    private final long remainingMillis;

    public ExerciseTickEvent(Object source, long remainingMillis) {
        super(source);
        this.remainingMillis = remainingMillis;
    }

    public long getRemainingMillis() {
        return remainingMillis;
    }
}
