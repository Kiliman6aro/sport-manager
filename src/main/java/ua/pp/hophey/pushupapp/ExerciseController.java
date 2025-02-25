package ua.pp.hophey.pushupapp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.Objects;

public class ExerciseController {

    @FXML
    private TextField repetitionsField;  // Текстовое поле для ввода количества повторений
    @FXML
    private Label statusLabel;  // Лейбл для отображения статуса
    @FXML
    private Button startButton;  // Кнопка для начала упражнений

    private int currentRepetition = 0;  // Счётчик текущего повторения
    private int totalRepetitions = 0;  // Общее количество повторений в подходе

    // Звуки
    private MediaPlayer oneSound;
    private MediaPlayer twoSound;
    private MediaPlayer finishSound;

    private Timeline exerciseTimeline;  // Для управления циклом с паузами

    public void initialize() {
        // Инициализация звуков
        oneSound = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/ua/pp/hophey/pushupapp/sounds/one.mp3")).toExternalForm()));
        twoSound = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/ua/pp/hophey/pushupapp/sounds/two.mp3")).toExternalForm()));
        finishSound = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/ua/pp/hophey/pushupapp/sounds/finish.mp3")).toExternalForm()));

        // Обработчик нажатия кнопки "Начать"
        startButton.setOnAction(event -> startExercise());

        // Инициализация Timeline
        exerciseTimeline = new Timeline();
        exerciseTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void startExercise() {
        // Проверка ввода
        try {
            totalRepetitions = Integer.parseInt(repetitionsField.getText());
            if (totalRepetitions <= 0) {
                statusLabel.setText("Введите число больше 0");
                return;
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Введите корректное число");
            return;
        }

        // Сброс текущего состояния
        currentRepetition = 0;
        statusLabel.setText("Начинаем!");
        startButton.setDisable(true);

        // Очистка и настройка Timeline
        exerciseTimeline.getKeyFrames().clear();
        exerciseTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2), event -> playNextRepetition()));

        // Первое повторение без задержки
        playNextRepetition(); // Сразу воспроизводим "one"
        if (totalRepetitions > 1) { // Запускаем таймер только если больше 1 повторения
            exerciseTimeline.play();
        }
    }

    private void playNextRepetition() {
        currentRepetition++;

        if (currentRepetition > totalRepetitions) {
            // Завершение сета
            exerciseTimeline.stop();
            startButton.setDisable(false);
            statusLabel.setText("Готово!");
            return;
        }

        // Определяем, последний ли это шаг
        boolean isLastRepetition = (currentRepetition == totalRepetitions);

        // Воспроизведение звука
        if (isLastRepetition) {
            finishSound.stop();
            finishSound.play();
            statusLabel.setText("Финиш!");
        } else {
            MediaPlayer soundToPlay = (currentRepetition % 2 == 1) ? oneSound : twoSound;
            soundToPlay.stop();
            soundToPlay.play();
            statusLabel.setText("Повторение " + currentRepetition + " из " + totalRepetitions);
        }

        // Останавливаем Timeline, если это последнее повторение
        if (isLastRepetition) {
            exerciseTimeline.stop();
            startButton.setDisable(false);
        }
    }

    // Очистка ресурсов
    public void shutdown() {
        exerciseTimeline.stop();
        oneSound.dispose();
        twoSound.dispose();
        finishSound.dispose();
    }
}