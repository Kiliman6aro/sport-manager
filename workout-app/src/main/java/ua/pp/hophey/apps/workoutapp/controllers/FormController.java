package ua.pp.hophey.apps.workoutapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class FormController {

    @FXML
    private Button saveButton;


    public void initialize() {
        System.out.println("FormController initialized!");
    }

    @FXML
    private void saveSettings() {
        System.out.println("Settings saved! (Stub)");
    }
}
