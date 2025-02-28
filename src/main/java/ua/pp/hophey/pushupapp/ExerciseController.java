package ua.pp.hophey.pushupapp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import ua.pp.hophey.pushupapp.Exercise.ExerciseHandler;
import ua.pp.hophey.pushupapp.Exercise.ExerciseSet;
import ua.pp.hophey.pushupapp.Exercise.SetHandler;

import java.util.Objects;

public class ExerciseController implements ExerciseHandler, SetHandler {

    @FXML
    private TextField repetitionsField;
    @FXML
    private Spinner<Double> timeForRepeat;
    @FXML
    private Label statusLabel;
    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    private ExerciseSet exerciseSet;

    private MediaPlayer oneSound;
    private MediaPlayer twoSound;
    private MediaPlayer finishSound;

    public void initialize() {
        oneSound = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/ua/pp/hophey/pushupapp/sounds/one.mp3")).toExternalForm()));
        twoSound = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/ua/pp/hophey/pushupapp/sounds/two.mp3")).toExternalForm()));
        finishSound = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/ua/pp/hophey/pushupapp/sounds/finish.mp3")).toExternalForm()));

        SpinnerValueFactory.DoubleSpinnerValueFactory timeFactory =
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0.5, 10.0, 1.5, 0.5);
        timeForRepeat.setValueFactory(timeFactory);
        timeForRepeat.setEditable(true);

        startButton.setOnAction(event -> startExercise());
        stopButton.setOnAction(event -> stopExercise());
    }

    private void stopExercise() {
        if (exerciseSet != null) {
            exerciseSet.stop();
            statusLabel.setText("Упражнение остановлено!");
        }
    }

    private void startExercise() {
        int repetitions;
        try {
            repetitions = Integer.parseInt(repetitionsField.getText());
            if (repetitions <= 0) {
                statusLabel.setText("Введите число повторений больше 0");
                return;
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Введите корректное число повторений");
            return;
        }

        double pauseDuration = timeForRepeat.getValue();
        if (pauseDuration <= 0) {
            statusLabel.setText("Время паузы должно быть больше 0");
            return;
        }

        statusLabel.setText("Начинаем!");
        startButton.setDisable(true);
        exerciseSet = new ExerciseSet(repetitions, pauseDuration, this, this);
        exerciseSet.start();
    }

    @Override
    public void onExerciseStart(int currentRepetition, int totalRepetitions) {
        Platform.runLater(() -> { // Обновление UI в потоке JavaFX
            oneSound.stop();
            oneSound.play();
            statusLabel.setText("Повторение " + currentRepetition + " из " + totalRepetitions + ": One");
        });
    }

    @Override
    public void onExerciseEnd(int currentRepetition, int totalRepetitions) {
        Platform.runLater(() -> {
            twoSound.stop();
            twoSound.play();
            statusLabel.setText("Повторение " + currentRepetition + " из " + totalRepetitions + ": Two");
        });
    }

    @Override
    public void onSetStart(int totalRepetitions) {
        //TODO: реализовать обработчик начала подхода
    }

    @Override
    public void onSetComplete(int totalRepetitions) {
        Platform.runLater(() -> {
            finishSound.stop();
            finishSound.play();
            statusLabel.setText("Финиш!");
            startButton.setDisable(false);
        });
    }

    public void shutdown() {
        if (exerciseSet != null) {
            exerciseSet.stop();
        }
        oneSound.dispose();
        twoSound.dispose();
        finishSound.dispose();
    }
}