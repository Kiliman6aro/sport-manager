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

    private int currentRepetition = 0;  // Счётчик завершённых повторений (циклов one-two)
    private int totalRepetitions = 0;  // Общее количество повторений в подходе
    private boolean isOnePlayed = false; // Флаг для отслеживания этапа в цикле (one или two)

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
        isOnePlayed = false;
        statusLabel.setText("Начинаем!");
        startButton.setDisable(true);

        // Очистка и настройка Timeline
        exerciseTimeline.getKeyFrames().clear();
        exerciseTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2), event -> playNextStep()));

        // Первое воспроизведение "one" без задержки
        playNextStep();
        if (totalRepetitions > 0) { // Запускаем таймер для продолжения
            exerciseTimeline.play();
        }
    }

    private void playNextStep() {
        if (!isOnePlayed) {
            // Воспроизводим "one"
            oneSound.stop();
            oneSound.play();
            statusLabel.setText("Повторение " + (currentRepetition + 1) + " из " + totalRepetitions + ": One");
            isOnePlayed = true;
        } else {
            // Воспроизводим "two" и завершаем повторение
            twoSound.stop();
            twoSound.play();
            currentRepetition++; // Увеличиваем счётчик только после "two"
            statusLabel.setText("Повторение " + currentRepetition + " из " + totalRepetitions + ": Two");

            // Проверяем, завершён ли сет
            if (currentRepetition == totalRepetitions) {
                exerciseTimeline.stop();
                finishSound.stop();
                finishSound.play();
                statusLabel.setText("Финиш!");
                startButton.setDisable(false);
                return;
            }

            isOnePlayed = false; // Сбрасываем флаг для следующего "one"
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