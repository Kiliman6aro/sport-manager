package ua.pp.hophey.libs.workout.repository.impl;

import ua.pp.hophey.libs.workout.model.TrainingSession;
import ua.pp.hophey.libs.workout.repository.TrainingSessionRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class InMemoryTrainingSessionRepository implements TrainingSessionRepository {
    private final List<TrainingSession> data = new ArrayList<>();



    public void add(TrainingSession session) {
        data.add(session);
    }

    @Override
    public List<TrainingSession> findByDate(LocalDate date) {
        return data.stream()
                .filter(session -> session.getStartDate().isEqual(date))
                .collect(toList());
    }

    @Override
    public List<TrainingSession> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return data.stream()
                .filter(session -> !session.getStartDate().isBefore(startDate)) // Начинается не раньше startDate
                .filter(session -> !session.getStartDate().isAfter(endDate))    // Начинается не позже endDate
                .collect(toList());
    }
}
