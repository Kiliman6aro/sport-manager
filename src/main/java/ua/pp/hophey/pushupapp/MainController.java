package ua.pp.hophey.pushupapp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class MainController {

    @FXML
    private Label welcomeText; // Для вывода текста
    @FXML
    private Button startButton; // Кнопка старта
    @FXML
    private Label timerLabel; // Для отображения таймера
    @FXML
    private TextField setCountField; // Ввод количества подходов

    private int timerSeconds = 0; // Время в секундах для таймера
    private Timeline timeline;

    @FXML
    protected void onStartButtonClick() {
        // Запуск тренировки
        int setsCount = Integer.parseInt(setCountField.getText());
        welcomeText.setText("Тренировка началась! " + setsCount + " подходов!");

        // Настройка таймера
        setupTimer();
        startTimer();
    }

    private void setupTimer() {
        // Создаем анимацию для таймера (каждую секунду обновляется)
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    timerSeconds++;
                    updateTimerDisplay();
                    if (timerSeconds >= 90) {
                        timerSeconds = 0;  // Сброс таймера
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);  // Бесконечный цикл
    }

    private void startTimer() {
        timeline.play();  // Запуск таймера
    }

    private void updateTimerDisplay() {
        int minutes = timerSeconds / 60;
        int seconds = timerSeconds % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));  // Отображаем время
    }
}