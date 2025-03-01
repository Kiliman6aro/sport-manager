module ua.pp.hophey.apps.workoutapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires ua.pp.hophey.libs.workout;


    opens ua.pp.hophey.apps.workoutapp to javafx.fxml;
    exports ua.pp.hophey.apps.workoutapp;
    exports ua.pp.hophey.apps.workoutapp.controllers;
}