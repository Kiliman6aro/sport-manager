package ua.pp.hophey.libs.workout.model;



import java.util.List;
import ua.pp.hophey.libs.workout.event.EventBus;
import ua.pp.hophey.libs.workout.event.workout.WorkoutStartedEvent;
import ua.pp.hophey.libs.workout.event.workout.WorkoutFinishedEvent;
import ua.pp.hophey.libs.workout.event.workout.RestTickEvent;

public class Workout{
    private final List<ExerciseSet> sets;

    public Workout(List<ExerciseSet> sets) {
        this.sets = sets;
    }

    public void start() {
        EventBus.getInstance().post(new WorkoutStartedEvent(this));
        for (ExerciseSet set : sets) {
            set.start();
            if (sets.indexOf(set) < sets.size() - 1) {
                long remaining = 5000;
                while (remaining >= 0) {
                    EventBus.getInstance().post(new RestTickEvent(this, remaining));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    remaining -= 1000;
                }
            }
        }
        EventBus.getInstance().post(new WorkoutFinishedEvent(this));
    }
}