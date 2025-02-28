package ua.pp.hophey.pushupapp.workoutlib.event;

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
