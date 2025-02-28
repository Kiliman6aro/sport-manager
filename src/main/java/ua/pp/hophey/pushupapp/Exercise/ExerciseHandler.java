package ua.pp.hophey.pushupapp.Exercise;

public interface ExerciseHandler {
    void onExerciseStart(int currentRepetition, int totalRepetitions);  // Начало повторения
    void onExerciseEnd(int currentRepetition, int totalRepetitions);   // Конец повторения
}
