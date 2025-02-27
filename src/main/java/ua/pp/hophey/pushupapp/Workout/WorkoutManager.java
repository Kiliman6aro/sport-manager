package ua.pp.hophey.pushupapp.Workout;

import ua.pp.hophey.pushupapp.Exercise.ExerciseSet;

import java.util.List;

public class WorkoutManager {
    private final List<ExerciseSet> exerciseSets; // Список всех подходов
    private final long restDurationMillis; // Длительность паузы между подходами
    private final WorkoutHandler handler; // Обработчик событий
    private volatile boolean running = false; // Флаг для остановки
    private int currentSetIndex = 0; // Индекс текущего подхода

    public WorkoutManager(List<ExerciseSet> exerciseSets, double restDurationSeconds, WorkoutHandler handler) {
        this.exerciseSets = exerciseSets;
        this.restDurationMillis = (long) (restDurationSeconds * 1000);
        this.handler = handler;
    }

    public void start() {
        if (exerciseSets.isEmpty()) {
            throw new IllegalStateException("Нет упражнений для выполнения!");
        }

        running = true;
        handler.onWorkoutStart(exerciseSets.size());
        startNextSet();
    }

    public void stop() {
        running = false;
        if (currentSetIndex < exerciseSets.size()) {
            exerciseSets.get(currentSetIndex).stop(); // Останавливаем текущий подход
        }
    }

    private void startNextSet() {
        if (!running || currentSetIndex >= exerciseSets.size()) {
            handler.onWorkoutComplete(); // Все подходы завершены
            return;
        }
        currentSetIndex++;
        ExerciseSet currentSet = exerciseSets.get(currentSetIndex);
        handler.onWorkoutSetStart(currentSetIndex, currentSet.getTotalRepetitions());
        currentSet.start();
    }

    private void startRestPeriod() {
        if (!running || currentSetIndex >= exerciseSets.size()) {
            handler.onWorkoutComplete();
            return;
        }

        new Thread(() -> {
            long remainingTime = restDurationMillis;
            while (running && remainingTime > 0) {
                handler.onWorkoutRest((int) (remainingTime / 1000));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
                remainingTime -= 1000;
            }
            startNextSet();
        }).start();
    }
}
