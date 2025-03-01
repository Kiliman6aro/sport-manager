package ua.pp.hophey.apps.workoutapp.controllers;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import ua.pp.hophey.libs.workout.event.EventBus;
import ua.pp.hophey.libs.workout.event.exercise.ExerciseFinishedEvent;
import ua.pp.hophey.libs.workout.event.exercise.ExerciseStartedEvent;
import ua.pp.hophey.libs.workout.event.exercise.ExerciseTickEvent;
import ua.pp.hophey.libs.workout.event.sets.SetFinishedEvent;
import ua.pp.hophey.libs.workout.event.sets.SetStartedEvent;
import ua.pp.hophey.libs.workout.event.workout.RestTickEvent;
import ua.pp.hophey.libs.workout.event.workout.WorkoutFinishedEvent;
import ua.pp.hophey.libs.workout.event.workout.WorkoutStartedEvent;
import ua.pp.hophey.libs.workout.model.Exercise;
import ua.pp.hophey.libs.workout.model.ExerciseSet;
import ua.pp.hophey.libs.workout.model.Workout;

import java.util.ArrayList;
import java.util.List;
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
        Workout w = new Workout(list);
        EventBus bus = EventBus.getInstance();
        bus.subscribe(WorkoutStartedEvent.class, event ->
                Platform.runLater(() -> statusLabel.setText("Тренировка началась")));
        bus.subscribe(WorkoutFinishedEvent.class, event ->
                Platform.runLater(() -> statusLabel.setText("Тренировка завершена")));
        bus.subscribe(SetStartedEvent.class, event ->
                Platform.runLater(() -> statusLabel.setText("Подход начался")));
        bus.subscribe(SetFinishedEvent.class, event ->
                Platform.runLater(() -> {
                    statusLabel.setText("Подход завершился");
                    finishSound.play();
                }));
        bus.subscribe(ExerciseStartedEvent.class, event ->
                Platform.runLater(() -> {
                    statusLabel.setText("Упражнение началось");
                    oneSound.play();
                    timerLabel.setText(event.getDurationMillis() / 1000 + " сек");
                }));
        bus.subscribe(ExerciseFinishedEvent.class, event ->
                Platform.runLater(() -> {
                    statusLabel.setText("Упражнение завершено");
                    twoSound.play();
                    timerLabel.setText("");
                }));
        bus.subscribe(ExerciseTickEvent.class, event ->
                Platform.runLater(() -> timerLabel.setText(event.getRemainingMillis() / 1000 + " сек")));
        bus.subscribe(RestTickEvent.class, event ->
                Platform.runLater(() -> {
                    statusLabel.setText("Перерыв");
                    timerLabel.setText(event.getRemainingMillis() / 1000 + " сек");
                }));
        new Thread(w::start).start();
        statusLabel.setText("Подход начался");
    }
}