package ua.pp.hophey.libs.workout.services;

import ua.pp.hophey.libs.workout.model.TrainingSession;
import ua.pp.hophey.libs.workout.repository.TrainingSessionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class WorkoutService {

    private final TrainingSessionRepository repository;
    private final TrainingSessionService sessionService;

    public WorkoutService(TrainingSessionRepository repository, TrainingSessionService sessionService) {
        this.repository = repository;
        this.sessionService = sessionService;
    }


    public List<TrainingSession> getWorkoutsBetweenDates(LocalDate startDate, LocalDate endDate) {
        List<TrainingSession> baseSessions = repository.findByDateRange(startDate, endDate);
        List<TrainingSession> allWorkouts = sessionService.getSessionsInRange(baseSessions, startDate, endDate);
        return allWorkouts.stream()
                .distinct()
                .collect(Collectors.toList());
    }
}
