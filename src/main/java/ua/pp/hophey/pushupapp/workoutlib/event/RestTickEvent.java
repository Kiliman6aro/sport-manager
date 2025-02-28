package ua.pp.hophey.pushupapp.workoutlib.event;

public class RestTickEvent extends WorkoutEvent {
    private final long remainingMillis;

    public RestTickEvent(Object source, long remainingMillis) {
        super(source);
        this.remainingMillis = remainingMillis;
    }

    public long getRemainingMillis() {
        return remainingMillis;
    }
}
