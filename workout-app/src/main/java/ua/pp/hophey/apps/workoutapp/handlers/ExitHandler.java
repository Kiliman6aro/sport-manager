package ua.pp.hophey.apps.workoutapp.handlers;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExitHandler {
    private final Stage stage;

    public ExitHandler(Stage stage) {
        this.stage = stage;
    }

    public void handleExit() {
        showExitConfirmation();
    }

    private void showExitConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to exit?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                performExit();
            }
        });
    }

    private void performExit() {
        Task<Void> saveTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                saveConfig();
                return null;
            }

            @Override
            protected void succeeded() {
                System.out.println("Config saved successfully");
                Platform.runLater(() -> closeStage());
            }

            @Override
            protected void failed() {
                System.out.println("Config save failed: " + getException().getMessage());
                Platform.runLater(() -> closeStage());
            }
        };
        new Thread(saveTask).start();
    }

    private void saveConfig() throws IOException {
        Path configDir = Paths.get(System.getProperty("user.home"), ".workoutapp");
        Path configFile = configDir.resolve("config.txt");
        if (!Files.exists(configDir)) {
            Files.createDirectories(configDir);
        }
        long exitTimeMs = System.currentTimeMillis();
        Files.writeString(configFile, "exit_time_ms=" + exitTimeMs);
        System.out.println("Config saved to: " + configFile);
    }

    private void closeStage() {
        if (stage != null) {
            stage.close();
        }
    }
}