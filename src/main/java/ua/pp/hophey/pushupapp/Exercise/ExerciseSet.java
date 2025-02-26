package ua.pp.hophey.pushupapp.Exercise;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class ExerciseSet {
    private final int totalRepetitions;      // Общее количество повторений
    private final double pauseDuration;      // Длительность паузы между шагами
    private final ExerciseHandler handler;   // Обработчик событий

    private int currentRepetition = 0;       // Счётчик завершённых повторений
    private boolean isExerciseStarted = false; // Флаг этапа в цикле
    private final Timeline exerciseTimeline;

    public ExerciseSet(int totalRepetitions, double pauseDuration, ExerciseHandler handler) {
        this.totalRepetitions = totalRepetitions;
        this.pauseDuration = pauseDuration;
        this.handler = handler;

        exerciseTimeline = new Timeline();
        exerciseTimeline.setCycleCount(Timeline.INDEFINITE);
        exerciseTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(pauseDuration), event -> executeNextStep()));
    }

    public void start() {
        if (totalRepetitions <= 0 || pauseDuration <= 0) {
            throw new IllegalArgumentException("Количество повторений и пауза должны быть больше 0");
        }

        currentRepetition = 0;
        isExerciseStarted = false;

        // Первый шаг без задержки
        executeNextStep();
        exerciseTimeline.play();
    }

    public void stop() {
        exerciseTimeline.stop();
    }

    private void executeNextStep() {
        if (!isExerciseStarted) {
            handler.onExerciseStart(currentRepetition + 1, totalRepetitions);
            isExerciseStarted = true;
        } else {
            handler.onExerciseEnd(currentRepetition + 1, totalRepetitions);
            currentRepetition++;
            isExerciseStarted = false;

            if (currentRepetition == totalRepetitions) {
                exerciseTimeline.stop();
                handler.onSetComplete(totalRepetitions);
            }
        }
    }
}
