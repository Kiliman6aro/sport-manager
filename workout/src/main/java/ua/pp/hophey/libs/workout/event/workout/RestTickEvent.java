package ua.pp.hophey.libs.workout.event.workout;


import ua.pp.hophey.libs.workout.event.WorkoutEvent;

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
