package ua.pp.hophey.libs.workout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.pp.hophey.libs.workout.model.TrainingSession;
import ua.pp.hophey.libs.workout.repository.MockTrainingSessionRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MockTrainingSessionRepositoryTest {
    private MockTrainingSessionRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MockTrainingSessionRepository();
        // Добавляем тестовые данные
        repository.add(new TrainingSession(1L, LocalDate.of(2025, 3, 1), LocalTime.of(8, 0), "Daily Session", "daily", 1, null));
        repository.add(new TrainingSession(2L, LocalDate.of(2025, 3, 4), LocalTime.of(9, 0), "Weekly Session", "weekly", 1, null));
        repository.add(new TrainingSession(3L, LocalDate.of(2025, 3, 5), LocalTime.of(10, 0), "Monthly Session", "monthly", 1, null));
        repository.add(new TrainingSession(4L, LocalDate.of(2025, 2, 1), LocalTime.of(11, 0), "Expired Session", "daily", 1, LocalDate.of(2025, 3, 31)));
    }

    @Test
    void shouldFindSessionsByDate() {
        System.out.println("Тест: Проверка поиска сессий по точной дате (ожидается только сессия от 2025-03-05)");
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
        System.out.println("Тест: Проверка поиска сессий в диапазоне дат с 2025-04-01 по 2025-04-07 (ожидаются 0 активные сессии)");
        LocalDate startDate = LocalDate.of(2025, 4, 1);
        LocalDate endDate = LocalDate.of(2025, 4, 7);
        List<TrainingSession> result = repository.findByDateRange(startDate, endDate);

        assertEquals(0, result.size()); // Ожидаем 3 сессии: ID 1, 2, 3 (ID 4 истекла)
    }

    @Test
    void shouldFindSessionsByDateRangeThreeResult() {
        System.out.println("Тест: Проверка поиска сессий в диапазоне дат с 2025-03-01 по 2025-03-07 (ожидаются 3 активные сессии)");
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
        System.out.println("Тест: Проверка поиска сессий в диапазоне дат с 2025-03-04 по 2025-03-05 (ожидаются 2 активные сессии)");
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
        System.out.println("Тест: Проверка поиска сессий в диапазоне дат с 2025-03-05 по 2025-03-05 (ожидаются 1 активные сессии)");
        LocalDate startDate = LocalDate.of(2025, 3, 5);
        LocalDate endDate = LocalDate.of(2025, 3, 5);
        List<TrainingSession> result = repository.findByDateRange(startDate, endDate);

        assertEquals(1, result.size());
        assertTrue(result.stream().anyMatch(s -> s.getId() == 3L));
    }

    @Test
    void shouldAddSession() {
        System.out.println("Тест: Проверка добавления новой сессии и её поиска по дате 2025-04-10");
        TrainingSession newSession = new TrainingSession(5L, LocalDate.of(2025, 4, 10), LocalTime.of(12, 0), "New Session", "weekly", 1, null);
        repository.add(newSession);

        List<TrainingSession> result = repository.findByDate(LocalDate.of(2025, 4, 10));
        assertEquals(1, result.size()); // Ожидаем 1 сессию
        assertTrue(result.stream().anyMatch(s -> s.getId() == 5L));
        assertEquals("New Session", result.get(0).getName());
    }
}