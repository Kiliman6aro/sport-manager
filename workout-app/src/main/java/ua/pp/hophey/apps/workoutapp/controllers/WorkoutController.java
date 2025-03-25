package ua.pp.hophey.apps.workoutapp.controllers;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import ua.pp.hophey.libs.workout.model.Exercise;

import java.util.Objects;

public class WorkoutController {


    public VBox container;
    public Label statusLabel;
    public Label timerLabel;
    private AudioClip oneSound;
    private AudioClip twoSound;
    private AudioClip finishSound;



    public void initialize() {
        oneSound = new AudioClip(Objects.requireNonNull(getClass().getResource("/ua/pp/hophey/apps/workoutapp/sounds/one.mp3")).toExternalForm());
        twoSound = new AudioClip(Objects.requireNonNull(getClass().getResource("/ua/pp/hophey/apps/workoutapp/sounds/two.mp3")).toExternalForm());
        finishSound = new AudioClip(Objects.requireNonNull(getClass().getResource("/ua/pp/hophey/apps/workoutapp/sounds/finish.mp3")).toExternalForm());


        Exercise e = new Exercise("Test", 2, 5, 60, 2);
        e.run();
    }
}