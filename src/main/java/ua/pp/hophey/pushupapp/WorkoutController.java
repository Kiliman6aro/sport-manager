package ua.pp.hophey.pushupapp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ua.pp.hophey.pushupapp.workoutlib.event.EventListener;
import ua.pp.hophey.pushupapp.workoutlib.model.Exercise;
import ua.pp.hophey.pushupapp.workoutlib.model.ExerciseSet;
import ua.pp.hophey.pushupapp.workoutlib.model.Workout;
import ua.pp.hophey.pushupapp.workoutlib.event.EventBus;

import java.util.ArrayList;
import java.util.List;

public class WorkoutController implements EventListener {
    @FXML
    private Label statusLabel;
    @FXML
    private Label timerLabel;

    public void initialize() {

        List<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise(2000)); // 1 секунда
        exercises.add(new Exercise(2000)); // 2 секунды
        exercises.add(new Exercise(2000)); // 1.5 секунды

        ExerciseSet exerciseSet1 = new ExerciseSet(exercises);
        ExerciseSet exerciseSet2 = new ExerciseSet(exercises);
        ExerciseSet exerciseSet3 = new ExerciseSet(exercises);
        List<ExerciseSet> list = new ArrayList<>();
        list.add(exerciseSet1);
        list.add(exerciseSet2);
        list.add(exerciseSet3);
        Workout  w = new Workout(list);
        EventBus.getInstance().subscribe(this);
        new Thread(w::start).start();
        statusLabel.setText("Подход начался");
    }

    @Override
    public void onEvent(String message) {
        Platform.runLater(() -> {
            statusLabel.setText(message);
        });
    }
}