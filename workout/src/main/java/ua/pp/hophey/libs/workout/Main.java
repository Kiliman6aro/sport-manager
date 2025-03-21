package ua.pp.hophey.libs.workout;

import ua.pp.hophey.libs.workout.model.Exercise;


public class Main {
    public static void main(String[] args) {

        Exercise squats = new Exercise("Спаринг", 2, 5, 60, 2);
        squats.run();
        System.out.println(squats);
    }
}