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


    public List<TrainingSession> getTrainingSessionsForDate(LocalDate date) {
        return repository.findByDate(date).stream()
                .filter(session -> session.getEndDate() == null || session.getEndDate().isAfter(date) || session.getEndDate().isEqual(date))
                .filter(session -> session.getRecurrence().matches(date, session.getStartDate()))
                .map(session -> new TrainingSession(
                        session.getId(),
                        date,
                        session.getStartTime(),
                        session.getName(),
                        session.getRecurrenceType(),
                        session.getRecurrenceInterval(),
                        session.getEndDate()))
                .collect(toList());
    }
}
