package ua.pp.hophey.pushupapp.workoutlib.model;

import ua.pp.hophey.pushupapp.workoutlib.event.EventBus;

import java.util.List;

public class ExerciseSet implements Runnable{
    private final List<Exercise> exercises;

    public ExerciseSet(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public void start() {
//        System.out.println("Подход начался");
        EventBus.getInstance().post("Подход начался");
        for (Exercise exercise : exercises) {
            try{
                Thread.sleep(exercise.getDurationMillis());
                exercise.start();
                Thread.sleep(exercise.getDurationMillis());
                exercise.stop();
            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
//        System.out.println("Подход завершился");
        EventBus.getInstance().post("Подход завершился");
    }

    @Override
    public void run() {
        start();
    }
}
