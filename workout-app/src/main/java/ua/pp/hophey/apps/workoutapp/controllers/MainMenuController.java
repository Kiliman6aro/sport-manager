package ua.pp.hophey.apps.workoutapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import ua.pp.hophey.apps.workoutapp.di.Container;
import ua.pp.hophey.apps.workoutapp.handlers.ExitHandler;
import ua.pp.hophey.libs.workout.manager.impl.BaseExerciseManager;
import ua.pp.hophey.libs.workout.model.Exercise;

import java.io.IOException;
import java.util.Objects;

public class MainMenuController {


    public VBox contentArea;

    private AudioClip oneSound;
    private AudioClip twoSound;
    private AudioClip finishSound;



    public void initialize() {
        oneSound = new AudioClip(Objects.requireNonNull(getClass().getResource("/ua/pp/hophey/apps/workoutapp/sounds/one.mp3")).toExternalForm());
        twoSound = new AudioClip(Objects.requireNonNull(getClass().getResource("/ua/pp/hophey/apps/workoutapp/sounds/two.mp3")).toExternalForm());
        finishSound = new AudioClip(Objects.requireNonNull(getClass().getResource("/ua/pp/hophey/apps/workoutapp/sounds/finish.mp3")).toExternalForm());

        Exercise exercise = new Exercise("Test", 2, 5, 60, 2);
        exercise.setDebugMode(true);
        BaseExerciseManager manager = new BaseExerciseManager();
        manager.start(exercise);

    }

    public void handleStart(ActionEvent actionEvent) {

    }

    public void handleSettings(ActionEvent actionEvent) throws IOException {
        loadView("form-view.fxml");
    }

    public void handleAbout(ActionEvent actionEvent) {

    }

    public void handleExit(ActionEvent actionEvent) {
        Container.getInstance().getService(ExitHandler.class).handleExit();
    }

    public void handleOpen(ActionEvent actionEvent) {

    }

    public void handleNew(ActionEvent actionEvent) {

    }

    private void loadView(String fxmlPath) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ua/pp/hophey/apps/workoutapp/" + fxmlPath));
            Parent view = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            System.err.println("Error loading FXML: " + fxmlPath);
            e.printStackTrace();
            throw e; // Пробрасываем ошибку дальше
        }
    }
}
