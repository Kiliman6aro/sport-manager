package ua.pp.hophey.pushupapp.workoutlib.event;

public interface WorkoutListener {
    void onEvent(EventType eventType, Object source);
}
