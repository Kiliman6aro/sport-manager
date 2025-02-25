module ua.pp.hophey.pushupapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens ua.pp.hophey.pushupapp to javafx.fxml;
    exports ua.pp.hophey.pushupapp;
}