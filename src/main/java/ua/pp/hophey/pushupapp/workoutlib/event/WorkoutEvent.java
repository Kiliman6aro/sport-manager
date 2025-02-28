package ua.pp.hophey.pushupapp.workoutlib.event;

public abstract class WorkoutEvent {
    protected final Object source;

    public WorkoutEvent(Object source) {
        this.source = source;
    }

    public Object getSource() {
        return source;
    }
}
