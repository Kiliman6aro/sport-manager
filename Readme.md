## Build and deploy

```bash
mvn clean package
```

```bash
jpackage --name WorkoutUpApp --input workout-app/target/libs --main-jar workout-app-1.0-SNAPSHOT.jar --main-class ua.pp.hophey.apps.workoutapp.PushUpTrackerApplication --type app-image --dest target/dist --module-path "C:\Program Files\Microsoft\jdk-17.0.10.7-hotspot\jmods;workout-app/target/libs" --add-modules javafx.controls,javafx.fxml,javafx.media --win-console --verbose
```