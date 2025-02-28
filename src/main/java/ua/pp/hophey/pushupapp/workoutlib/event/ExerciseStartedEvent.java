package ua.pp.hophey.pushupapp.workoutlib.event;

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
