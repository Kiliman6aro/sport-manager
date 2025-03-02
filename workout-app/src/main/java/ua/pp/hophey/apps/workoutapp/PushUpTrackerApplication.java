package ua.pp.hophey.apps.workoutapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ua.pp.hophey.apps.workoutapp.controllers.MainMenuController;
import ua.pp.hophey.apps.workoutapp.di.Container;
import ua.pp.hophey.apps.workoutapp.handlers.ExitHandler;

import java.io.IOException;

public class PushUpTrackerApplication extends Application  {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        MainMenuController controller = fxmlLoader.getController();
        Container.getInstance().setService(ExitHandler.class, new ExitHandler(stage));
        stage.setOnCloseRequest(event -> {
            event.consume(); // Отменяем дефолтное закрытие
            Container.getInstance().getService(ExitHandler.class).handleExit();
        });
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}