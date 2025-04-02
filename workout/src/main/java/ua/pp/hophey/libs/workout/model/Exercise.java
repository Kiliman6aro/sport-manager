package ua.pp.hophey.libs.workout.model;

import ua.pp.hophey.libs.workout.event.EventBus;
import ua.pp.hophey.libs.workout.event.WorkoutInterruptedEvent;
import ua.pp.hophey.libs.workout.event.exercise.ExerciseFinishedEvent;
import ua.pp.hophey.libs.workout.event.exercise.ExerciseStartedEvent;
import ua.pp.hophey.libs.workout.event.exercise.ExerciseTickEvent;
import ua.pp.hophey.libs.workout.event.sets.SetFinishedEvent;
import ua.pp.hophey.libs.workout.event.sets.SetStartedEvent;
import ua.pp.hophey.libs.workout.event.workout.RestTickEvent;
import ua.pp.hophey.libs.workout.event.workout.WorkoutFinishedEvent;
import ua.pp.hophey.libs.workout.event.workout.WorkoutStartedEvent;

public class Exercise{
    private final String name;              // Название упражнения
    private final int setCount;             // Количество подходов
    private final int repetitions;          // Количество повторений в подходе
    private final int restTime;             // Перерыв между подходами (в секундах)
    private final int durationPerRep;       // Длительность одного повторения (в секундах)

    private boolean debugMode = false;

    public Exercise(String name, int setCount, int repetitions, int restTime, int durationPerRep) {
        this.name = name;
        this.setCount = setCount;
        this.repetitions = repetitions;
        this.restTime = restTime;
        this.durationPerRep = durationPerRep;
    }

    public String getName() {
        return name;
    }

    public int getSetCount() {
        return setCount;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public int getRestTime() {
        return restTime;
    }

    public int getDurationPerRep() {
        return durationPerRep;
    }

    @Override
    public String toString() {
        return String.format("%s: %d подходов, %d повторений в подходе, длительность %d сек, отдых %d сек",
                name, setCount, repetitions, durationPerRep, restTime);
    }

    public void run() {
        EventBus eventBus = EventBus.getInstance();
        log(String.format("Упражнение %s началось. [ %s ]%n", this.getName(), this));
        eventBus.post(new WorkoutStartedEvent(this));
        try {
            for (int i = 0; i < this.setCount; i++) {
                log(String.format("Начинаю подход %d %n", i + 1));
                eventBus.post(new SetStartedEvent(this));
                for (int j = 0; j < this.repetitions; j++) {
                    log(String.format("One: [%d] %n", j + 1));
                    eventBus.post(new ExerciseStartedEvent(this));
                    sleep((this.durationPerRep / 2) * 1000L);
                    log(String.format("Two..."));
                    sleep((this.durationPerRep / 2) * 1000L);
                    eventBus.post(new ExerciseTickEvent(this));
                    eventBus.post(new ExerciseFinishedEvent(this));
                }
                eventBus.post(new SetFinishedEvent(this));
                if (i < this.setCount - 1 && restTime > 0) {
                    log(String.format("Отдых между подходами"));
                    for (int r = 0; r < this.restTime; r++) {
                        sleep(1000L);
                        eventBus.post(new RestTickEvent(this));
                    }
                }
            }
            eventBus.post(new WorkoutFinishedEvent(this));
            log(String.format("Упражнение %s закончилось. [ %s ]%n", this.getName(), this));
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            eventBus.post(new WorkoutInterruptedEvent(this));
            log(String.format("Упражнение %s прервано. [ %s ]%n", this.getName(), this));
        }
    }

    private void log(String s) {
        if(this.debugMode){
            System.out.println(s);
        }
    }

    // Делаем sleep публичным для Mockito
    public void sleep(long millis) throws InterruptedException {
        Thread.sleep(millis);
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
}
