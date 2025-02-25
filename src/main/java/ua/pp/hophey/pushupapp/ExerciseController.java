package ua.pp.hophey.pushupapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class ExerciseController {

    @FXML
    private TextField repetitionsField;  // Текстовое поле для ввода количества повторений
    @FXML
    private Label statusLabel;  // Лейбл для отображения статуса
    @FXML
    private Button startButton;  // Кнопка для начала упражнений

    private int currentRepetition = 0;  // Счётчик повторений
    private int totalRepetitions = 0;  // Общее количество повторений в подходе

    // Звуки
    private MediaPlayer oneSound;
    private MediaPlayer twoSound;
    private MediaPlayer finishSound;

    public void initialize() {
        // Инициализация звуков
        oneSound = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/ua/pp/hophey/pushupapp/sounds/one.mp3")).toExternalForm()));
        twoSound = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/ua/pp/hophey/pushupapp/sounds/two.mp3")).toExternalForm()));
        finishSound = new MediaPlayer(new Media(Objects.requireNonNull(getClass().getResource("/ua/pp/hophey/pushupapp/sounds/finish.mp3")).toExternalForm()));

        // Обработчик нажатия кнопки "Начать"
        startButton.setOnAction(event -> startExercise());
    }

    private void startExercise() {
        try {
            // Получаем количество повторений из текстового поля
            totalRepetitions = Integer.parseInt(repetitionsField.getText());
            currentRepetition = 0;  // Сбрасываем счётчик

            // Обновляем статус
            statusLabel.setText("Выполняйте отжимания!");

            // Начинаем отслеживать повторения
            performPushUp();
        } catch (NumberFormatException e) {
            statusLabel.setText("Введите правильное количество повторений!");
        }
    }

    private void performPushUp() {
        // Сначала опускание
        if (currentRepetition < totalRepetitions) {
            playSound(oneSound);  // Проигрываем звук "one.mp3"
            currentRepetition++;
            statusLabel.setText("Опускание: " + currentRepetition + "/" + totalRepetitions);

            // После опускания подъем
            playSound(twoSound);  // Проигрываем звук "two.mp3"

            // Проверка, если это последний повтор
            if (currentRepetition == totalRepetitions) {
                playSound(finishSound);  // Проигрываем звук "finish.mp3"
                statusLabel.setText("Поздравляю! Ты завершил подход!");
            } else {
                // Если не последний повтор, продолжаем выполнять отжимания
                statusLabel.setText("Подъем: " + currentRepetition + "/" + totalRepetitions);
            }
        }
    }

    private void playSound(MediaPlayer sound) {
        sound.stop();
        sound.play();
    }
}
