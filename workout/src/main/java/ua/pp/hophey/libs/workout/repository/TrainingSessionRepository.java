package ua.pp.hophey.libs.workout.repository;

import ua.pp.hophey.libs.workout.model.TrainingSession;

import java.time.LocalDate;
import java.util.List;

public interface TrainingSessionRepository {
    List<TrainingSession> findByDate(LocalDate date);
    List<TrainingSession> findByDateRange(LocalDate startDate, LocalDate endDate);
    List<TrainingSession> findAllSessionsNotAfter(LocalDate date);
    void add(TrainingSession session);
}
