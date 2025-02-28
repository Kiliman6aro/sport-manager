package ua.pp.hophey.pushupapp.workoutlib.model;

import ua.pp.hophey.pushupapp.workoutlib.event.EventBus;

import java.util.List;

public class Workout implements Executable{
    private final List<ExerciseSet> sets;

    public Workout(List<ExerciseSet> sets) {
        this.sets = sets;
    }

    @Override
    public void start() {
//        System.out.println("Тренеровка началась");
        EventBus.getInstance().post("Тренеровка началась");
        for(ExerciseSet set: sets){
            set.start();
        }
//        System.out.println("Тренеровка завершилась");
        EventBus.getInstance().post("Тренеровка завершилась");
    }

    @Override
    public void stop() {

    }
}