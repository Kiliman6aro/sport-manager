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
        exerciseTimeline.setCycleCount(Timeline.INDEFINITE); // Будет работать, пока не остановим
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
        startButton.setDisable(true); // Блокируем кнопку во время выполнения

        // Очистка и настройка Timeline
        exerciseTimeline.getKeyFrames().clear();
        exerciseTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2), event -> playNextRepetition())); // Пауза 2 секунды между командами
        exerciseTimeline.play();
    }

    private void playNextRepetition() {
        currentRepetition++;

        if (currentRepetition > totalRepetitions) {
            // Завершение сета
            exerciseTimeline.stop();
            finishSound.stop(); // Сбрасываем звук перед воспроизведением
            finishSound.play();
            statusLabel.setText("Готово!");
            startButton.setDisable(false); // Разблокируем кнопку
            return;
        }

        // Воспроизведение звука в зависимости от чётности повторения
        MediaPlayer soundToPlay = (currentRepetition % 2 == 1) ? oneSound : twoSound;
        soundToPlay.stop(); // Сбрасываем звук перед воспроизведением, чтобы он играл заново
        soundToPlay.play();

        // Обновление статуса
        statusLabel.setText("Повторение " + currentRepetition + " из " + totalRepetitions);
    }

    // Метод для очистки ресурсов (вызывается при закрытии приложения, если нужно)
    public void shutdown() {
        exerciseTimeline.stop();
        oneSound.dispose();
        twoSound.dispose();
        finishSound.dispose();
    }
}