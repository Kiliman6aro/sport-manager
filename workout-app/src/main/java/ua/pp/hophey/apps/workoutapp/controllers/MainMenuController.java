package ua.pp.hophey.apps.workoutapp.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import ua.pp.hophey.apps.workoutapp.PushUpTrackerApplication;
import ua.pp.hophey.apps.workoutapp.di.Container;
import ua.pp.hophey.apps.workoutapp.handlers.ExitHandler;

import java.io.IOException;
import java.util.Arrays;

public class MainMenuController {


    public VBox contentArea;

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
