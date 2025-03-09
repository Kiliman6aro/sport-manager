package ua.pp.hophey.libs.workout.services;

import ua.pp.hophey.libs.workout.model.TrainingSession;
import ua.pp.hophey.libs.workout.repository.TrainingSessionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class WorkoutService {

    private final TrainingSessionRepository repository;

    public WorkoutService(TrainingSessionRepository repository) {
        this.repository = repository;
    }

}
