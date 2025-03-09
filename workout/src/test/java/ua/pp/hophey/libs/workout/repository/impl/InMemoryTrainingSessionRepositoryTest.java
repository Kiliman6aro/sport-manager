package ua.pp.hophey.libs.workout.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.pp.hophey.libs.workout.model.TrainingSession;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTrainingSessionRepositoryTest {
    private InMemoryTrainingSessionRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryTrainingSessionRepository();
        // Добавляем тестовые данные
        repository.add(new TrainingSession(1L, LocalDate.of(2025, 3, 1), LocalTime.of(8, 0), "Daily Session"));
        repository.add(new TrainingSession(2L, LocalDate.of(2025, 3, 4), LocalTime.of(9, 0), "Weekly Session"));
        repository.add(new TrainingSession(3L, LocalDate.of(2025, 3, 5), LocalTime.of(10, 0), "Monthly Session"));
        repository.add(new TrainingSession(4L, LocalDate.of(2025, 2, 1), LocalTime.of(11, 0), "Expired Session"));
    }

    @Test
    void shouldFindSessionsByDate() {
        LocalDate testDate = LocalDate.of(2025, 3, 5);
        List<TrainingSession> result = repository.findByDate(testDate);

        result.forEach(session -> System.out.println("ID: " + session.getId() + ", StartDate: " + session.getStartDate()));

        assertEquals(1, result.size());
        assertTrue(result.stream().anyMatch(s -> s.getId() == 3L));
        assertFalse(result.stream().anyMatch(s -> s.getId() == 1L));
        assertFalse(result.stream().anyMatch(s -> s.getId() == 2L));
        assertFalse(result.stream().anyMatch(s -> s.getId() == 4L));
    }

    @Test
    void shouldFindSessionsByDateRangeZeroResult() {
        LocalDate startDate = LocalDate.of(2025, 4, 1);
        LocalDate endDate = LocalDate.of(2025, 4, 7);
        List<TrainingSession> result = repository.findByDateRange(startDate, endDate);

        assertEquals(0, result.size()); // Ожидаем 3 сессии: ID 1, 2, 3 (ID 4 истекла)
    }

    @Test
    void shouldFindSessionsByDateRangeThreeResult() {
        LocalDate startDate = LocalDate.of(2025, 3, 1);
        LocalDate endDate = LocalDate.of(2025, 3, 7);
        List<TrainingSession> result = repository.findByDateRange(startDate, endDate);

        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(s -> s.getId() == 1L));
        assertTrue(result.stream().anyMatch(s -> s.getId() == 2L));
        assertTrue(result.stream().anyMatch(s -> s.getId() == 3L));
        assertFalse(result.stream().anyMatch(s -> s.getId() == 4L));
    }


    @Test
    void shouldFindSessionsByDateRangeTwoResult() {
        LocalDate startDate = LocalDate.of(2025, 3, 4);
        LocalDate endDate = LocalDate.of(2025, 3, 5);
        List<TrainingSession> result = repository.findByDateRange(startDate, endDate);

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(s -> s.getId() == 2L));
        assertTrue(result.stream().anyMatch(s -> s.getId() == 3L));
        assertFalse(result.stream().anyMatch(s -> s.getId() == 4L));
    }

    @Test
    void shouldFindSessionsByDateRangeOneResult() {
        LocalDate startDate = LocalDate.of(2025, 3, 5);
        LocalDate endDate = LocalDate.of(2025, 3, 5);
        List<TrainingSession> result = repository.findByDateRange(startDate, endDate);

        assertEquals(1, result.size());
        assertTrue(result.stream().anyMatch(s -> s.getId() == 3L));
    }

    @Test
    void shouldAddSession() {
        TrainingSession newSession = new TrainingSession(5L, LocalDate.of(2025, 4, 10), LocalTime.of(12, 0), "New Session");
        repository.add(newSession);

        List<TrainingSession> result = repository.findByDate(LocalDate.of(2025, 4, 10));
        assertEquals(1, result.size()); // Ожидаем 1 сессию
        assertTrue(result.stream().anyMatch(s -> s.getId() == 5L));
        assertEquals("New Session", result.get(0).getName());
    }
}