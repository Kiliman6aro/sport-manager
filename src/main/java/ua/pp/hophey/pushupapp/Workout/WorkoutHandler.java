package ua.pp.hophey.pushupapp.Workout;

public interface WorkoutHandler {
    void onWorkoutStart(int totalSets); // Начало тренировки
    void onWorkoutRest(int remainingTimeSeconds); // Обратный отсчет паузы
    void onWorkoutSetStart(int setIndex, int totalRepetitions); // Начало подхода
    void onWorkoutSetComplete(int setIndex); // Завершение подхода
    void onWorkoutComplete(); // Завершение тренировки
}
