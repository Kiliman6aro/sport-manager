package ua.pp.hophey.libs.workout.repository.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.pp.hophey.libs.workout.model.Exercise;
import ua.pp.hophey.libs.workout.model.TrainingSession;
import ua.pp.hophey.libs.workout.repository.TrainingSessionRepository;
import ua.pp.hophey.libs.workout.repository.impl.InMemoryTrainingSessionRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryTrainingSessionRepositoryTest {
    private TrainingSessionRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryTrainingSessionRepository();

        // Создаём сессии с упражнениями
        TrainingSession dailySession = new TrainingSession(1L, LocalDate.of(2025, 3, 1), LocalTime.of(8, 0), "Daily Session");
        dailySession.addExercise(new Exercise("Приседания", 2, 5, 60, 2));
        dailySession.addExercise(new Exercise("Отжимания", 3, 10, 30, 1));

        TrainingSession weeklySession = new TrainingSession(2L, LocalDate.of(2025, 3, 4), LocalTime.of(9, 0), "Weekly Session");
        weeklySession.addExercise(new Exercise("Подтягивания", 3, 8, 90, 3));

        TrainingSession monthlySession = new TrainingSession(3L, LocalDate.of(2025, 3, 5), LocalTime.of(10, 0), "Monthly Session");
        monthlySession.addExercise(new Exercise("Планка", 3, 1, 30, 60));

        TrainingSession expiredSession = new TrainingSession(4L, LocalDate.of(2025, 2, 1), LocalTime.of(11, 0), "Expired Session");
        expiredSession.addExercise(new Exercise("Бег", 1, 1, 0, 1800));

        // Добавляем тестовые данные в репозиторий
        repository.add(dailySession);
        repository.add(weeklySession);
        repository.add(monthlySession);
        repository.add(expiredSession);
    }

    @Test
    void shouldFindSessionByExactDate() {
        LocalDate testDate = LocalDate.of(2025, 3, 5);
        List<TrainingSession> result = repository.findByDate(testDate);

        assertThat(result)
                .as("Проверка сессий на 2025-03-05")
                .hasSize(1)
                .extracting(TrainingSession::getId)
                .containsExactly(3L);

        assertThat(result.get(0).getExercises())
                .as("Проверка упражнений в сессии с ID 3")
                .hasSize(1)
                .extracting(Exercise::getName)
                .containsExactly("Планка");
    }

    @Test
    void shouldReturnEmptyListForFutureDateRange() {
        LocalDate startDate = LocalDate.of(2025, 4, 1);
        LocalDate endDate = LocalDate.of(2025, 4, 7);
        List<TrainingSession> result = repository.findByDateRange(startDate, endDate);

        assertThat(result)
                .as("Проверка диапазона 2025-04-01 - 2025-04-07")
                .isEmpty();
    }

    @Test
    void shouldFindThreeSessionsInDateRange() {
        LocalDate startDate = LocalDate.of(2025, 3, 1);
        LocalDate endDate = LocalDate.of(2025, 3, 7);
        List<TrainingSession> result = repository.findByDateRange(startDate, endDate);

        assertThat(result)
                .as("Проверка диапазона 2025-03-01 - 2025-03-07")
                .hasSize(3)
                .extracting(TrainingSession::getId)
                .containsExactlyInAnyOrder(1L, 2L, 3L)
                .doesNotContain(4L);

        assertThat(result)
                .filteredOn(session -> session.getId() == 1L)
                .flatExtracting(TrainingSession::getExercises)
                .extracting(Exercise::getName)
                .containsExactlyInAnyOrder("Приседания", "Отжимания");
    }

    @Test
    void shouldFindTwoSessionsInNarrowDateRange() {
        LocalDate startDate = LocalDate.of(2025, 3, 4);
        LocalDate endDate = LocalDate.of(2025, 3, 5);
        List<TrainingSession> result = repository.findByDateRange(startDate, endDate);

        assertThat(result)
                .as("Проверка диапазона 2025-03-04 - 2025-03-05")
                .hasSize(2)
                .extracting(TrainingSession::getId)
                .containsExactlyInAnyOrder(2L, 3L)
                .doesNotContain(1L, 4L);
    }

    @Test
    void shouldFindOneSessionInSingleDayRange() {
        LocalDate startDate = LocalDate.of(2025, 3, 5);
        LocalDate endDate = LocalDate.of(2025, 3, 5);
        List<TrainingSession> result = repository.findByDateRange(startDate, endDate);

        assertThat(result)
                .as("Проверка диапазона 2025-03-05 - 2025-03-05")
                .hasSize(1)
                .extracting(TrainingSession::getId)
                .containsExactly(3L);
    }

    @Test
    void shouldAddSessionWithExercises() {
        TrainingSession newSession = new TrainingSession(5L, LocalDate.of(2025, 4, 10), LocalTime.of(12, 0), "New Session");
        newSession.addExercise(new Exercise("Бег", 1, 1, 0, 1800));
        repository.add(newSession);

        List<TrainingSession> result = repository.findByDate(LocalDate.of(2025, 4, 10));

        assertThat(result)
                .as("Проверка добавления сессии на 2025-04-10")
                .hasSize(1)
                .extracting(TrainingSession::getId)
                .containsExactly(5L);

        assertThat(result)
                .extracting(TrainingSession::getName)
                .containsExactly("New Session");

        assertThat(result.get(0).getExercises())
                .as("Проверка упражнений в новой сессии")
                .hasSize(1)
                .extracting(Exercise::getName)
                .containsExactly("Бег");
    }
}