package ua.pp.hophey.libs.workout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.pp.hophey.libs.workout.model.TrainingSession;
import ua.pp.hophey.libs.workout.repository.impl.InMemoryTrainingSessionRepository;
import ua.pp.hophey.libs.workout.services.WorkoutService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class WorkoutServiceTest {
    private WorkoutService service;
    private InMemoryTrainingSessionRepository mockRepository;

    @BeforeEach
    void setUp() {
        mockRepository = new InMemoryTrainingSessionRepository();
        service = new WorkoutService(mockRepository);

        mockRepository.add(new TrainingSession(1L, LocalDate.of(2025, 3, 1), LocalTime.of(8, 0), "Daily Session", "daily", 1, null));
        mockRepository.add(new TrainingSession(2L, LocalDate.of(2025, 3, 4), LocalTime.of(9, 0), "Weekly Session", "weekly", 1, null));
        mockRepository.add(new TrainingSession(3L, LocalDate.of(2025, 3, 5), LocalTime.of(10, 0), "Monthly Session", "monthly", 1, null));
        mockRepository.add(new TrainingSession(4L, LocalDate.of(2025, 2, 1), LocalTime.of(11, 0), "Expired Session", "daily", 1, LocalDate.of(2025, 3, 31)));
        mockRepository.add(new TrainingSession(5L, LocalDate.of(2025, 3, 1), LocalTime.of(12, 0), "Biweekly Session", "weekly", 2, null));
    }


    @Test
    void shouldExcludeExpiredSessionsForDate() {
        LocalDate testDate = LocalDate.of(2025, 4, 5);
        List<TrainingSession> result = service.getTrainingSessionsForDate(testDate);
        assertFalse(result.stream().anyMatch(s -> s.getId() == 4L)); // Используем == вместо equals
    }

    @Test
    void shouldHandleBiweeklySession() {
        LocalDate testDate = LocalDate.of(2025, 4, 1);
        List<TrainingSession> result = service.getTrainingSessionsForDate(testDate);
        assertFalse(result.stream().anyMatch(s -> s.getId() == 5L)); // Используем == вместо equals
    }
}