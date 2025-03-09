package ua.pp.hophey.libs.workout.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.pp.hophey.libs.workout.model.TrainingSession;
import ua.pp.hophey.libs.workout.strategy.recurrence.RecurrenceRule;
import ua.pp.hophey.libs.workout.strategy.recurrence.impl.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrainingSessionServiceTest {

    private TrainingSessionService service;

    @BeforeEach
    void setUp() {
        service = new TrainingSessionService();
    }

    // Вспомогательный метод для создания сессии
    private TrainingSession createSession(Long id, LocalDate startDate, RecurrenceRule rule, String name) {
        TrainingSession session = new TrainingSession(id, startDate, LocalTime.of(8, 0), name);
        session.setRecurrenceRule(rule);
        return session;
    }

    @Test
    void testDailyAndWeeklyInRange() {
        // Датасет 1: Daily с 10 марта, Weekly с 11 марта
        List<TrainingSession> sessions = new ArrayList<>();
        sessions.add(createSession(1L, LocalDate.of(2025, 3, 10), new DailyRecurrence(), "Daily"));
        sessions.add(createSession(2L, LocalDate.of(2025, 3, 11), new WeeklyRecurrence(1), "Weekly"));

        LocalDate startDate = LocalDate.of(2025, 3, 10);
        LocalDate endDate = LocalDate.of(2025, 3, 24);

        List<TrainingSession> result = service.getSessionsInRange(sessions, startDate, endDate);

        assertEquals(17, result.size(), "Ожидается 17 сессий (15 Daily + 2 Weekly)");

        List<LocalDate> dailyDates = result.stream()
                .filter(s -> s.getId() == 1L)
                .map(TrainingSession::getStartDate)
                .toList();
        assertEquals(15, dailyDates.size());
        assertTrue(dailyDates.containsAll(List.of(
                LocalDate.of(2025, 3, 10), LocalDate.of(2025, 3, 11), LocalDate.of(2025, 3, 12),
                LocalDate.of(2025, 3, 13), LocalDate.of(2025, 3, 14), LocalDate.of(2025, 3, 15),
                LocalDate.of(2025, 3, 16), LocalDate.of(2025, 3, 17), LocalDate.of(2025, 3, 18),
                LocalDate.of(2025, 3, 19), LocalDate.of(2025, 3, 20), LocalDate.of(2025, 3, 21),
                LocalDate.of(2025, 3, 22), LocalDate.of(2025, 3, 23), LocalDate.of(2025, 3, 24)
        )));

        List<LocalDate> weeklyDates = result.stream()
                .filter(s -> s.getId() == 2L)
                .map(TrainingSession::getStartDate)
                .toList();
        assertEquals(2, weeklyDates.size());
        assertTrue(weeklyDates.containsAll(List.of(
                LocalDate.of(2025, 3, 11), LocalDate.of(2025, 3, 18)
        )));
    }

    @Test
    void testMonthlyAndDayOfWeekWithSingle() {
        // Датасет 2: Monthly с 5 марта, DayOfWeek (Monday) с 10 марта, Single 15 марта
        List<TrainingSession> sessions = new ArrayList<>();
        sessions.add(createSession(1L, LocalDate.of(2025, 3, 5), new MonthlyRecurrence(1), "Monthly"));
        sessions.add(createSession(2L, LocalDate.of(2025, 3, 10), new DayOfWeekRecurrence(DayOfWeek.MONDAY, 1), "Monday"));
        sessions.add(createSession(3L, LocalDate.of(2025, 3, 15), null, "Single"));

        LocalDate startDate = LocalDate.of(2025, 3, 1);
        LocalDate endDate = LocalDate.of(2025, 5, 31);

        List<TrainingSession> result = service.getSessionsInRange(sessions, startDate, endDate);

        assertEquals(16, result.size(), "Ожидается 16 сессий (3 Monthly + 12 Mondays + 1 Single)");

        List<LocalDate> monthlyDates = result.stream()
                .filter(s -> s.getId() == 1L)
                .map(TrainingSession::getStartDate)
                .toList();
        assertEquals(3, monthlyDates.size());
        assertTrue(monthlyDates.containsAll(List.of(
                LocalDate.of(2025, 3, 5), LocalDate.of(2025, 4, 5), LocalDate.of(2025, 5, 5)
        )));

        List<LocalDate> mondayDates = result.stream()
                .filter(s -> s.getId() == 2L)
                .map(TrainingSession::getStartDate)
                .toList();
        assertEquals(12, mondayDates.size());
        assertTrue(mondayDates.containsAll(List.of(
                LocalDate.of(2025, 3, 10), LocalDate.of(2025, 3, 17), LocalDate.of(2025, 3, 24),
                LocalDate.of(2025, 3, 31), LocalDate.of(2025, 4, 7), LocalDate.of(2025, 4, 14),
                LocalDate.of(2025, 4, 21), LocalDate.of(2025, 4, 28), LocalDate.of(2025, 5, 5),
                LocalDate.of(2025, 5, 12), LocalDate.of(2025, 5, 19), LocalDate.of(2025, 5, 26)
        )));

        List<LocalDate> singleDates = result.stream()
                .filter(s -> s.getId() == 3L)
                .map(TrainingSession::getStartDate)
                .toList();
        assertEquals(1, singleDates.size());
        assertTrue(singleDates.contains(LocalDate.of(2025, 3, 15)));
    }

    @Test
    void testSingleSessionAndYearly() {
        // Датасет 3: Без повторения с 15 марта, Yearly с 20 марта
        List<TrainingSession> sessions = new ArrayList<>();
        sessions.add(createSession(1L, LocalDate.of(2025, 3, 15), null, "Single"));
        sessions.add(createSession(2L, LocalDate.of(2025, 3, 20), new YearlyRecurrence(), "Yearly"));

        LocalDate startDate = LocalDate.of(2025, 3, 1);
        LocalDate endDate = LocalDate.of(2026, 3, 31);

        List<TrainingSession> result = service.getSessionsInRange(sessions, startDate, endDate);

        assertEquals(3, result.size(), "Ожидается 3 сессии (1 Single + 2 Yearly)");

        List<LocalDate> singleDates = result.stream()
                .filter(s -> s.getId() == 1L)
                .map(TrainingSession::getStartDate)
                .toList();
        assertEquals(1, singleDates.size());
        assertTrue(singleDates.contains(LocalDate.of(2025, 3, 15)));

        List<LocalDate> yearlyDates = result.stream()
                .filter(s -> s.getId() == 2L)
                .map(TrainingSession::getStartDate)
                .toList();
        assertEquals(2, yearlyDates.size());
        assertTrue(yearlyDates.containsAll(List.of(
                LocalDate.of(2025, 3, 20), LocalDate.of(2026, 3, 20)
        )));
    }

    @Test
    void testEmptyRangeWithSingleOutOfRange() {
        // Датасет 4: Single вне диапазона, Daily тоже вне
        List<TrainingSession> sessions = new ArrayList<>();
        sessions.add(createSession(1L, LocalDate.of(2025, 3, 10), new DailyRecurrence(), "Daily"));
        sessions.add(createSession(2L, LocalDate.of(2025, 3, 15), null, "Single"));

        LocalDate startDate = LocalDate.of(2025, 4, 1);
        LocalDate endDate = LocalDate.of(2025, 4, 5);

        List<TrainingSession> result = service.getSessionsInRange(sessions, startDate, endDate);

        assertEquals(0, result.size(), "Ожидается 0 сессий, так как обе вне диапазона");
    }

    @Test
    void testMixedSingleAndWeekly() {
        // Датасет 5: Weekly с 10 марта, Single 12 марта и 14 марта
        List<TrainingSession> sessions = new ArrayList<>();
        sessions.add(createSession(1L, LocalDate.of(2025, 3, 10), new WeeklyRecurrence(1), "Weekly"));
        sessions.add(createSession(2L, LocalDate.of(2025, 3, 12), null, "Single1"));
        sessions.add(createSession(3L, LocalDate.of(2025, 3, 14), null, "Single2"));

        LocalDate startDate = LocalDate.of(2025, 3, 10);
        LocalDate endDate = LocalDate.of(2025, 3, 24);

        List<TrainingSession> result = service.getSessionsInRange(sessions, startDate, endDate);

        assertEquals(5, result.size(), "Ожидается 5 сессий (3 Weekly + 2 Single)");

        List<LocalDate> weeklyDates = result.stream()
                .filter(s -> s.getId() == 1L)
                .map(TrainingSession::getStartDate)
                .toList();
        assertEquals(3, weeklyDates.size());
        assertTrue(weeklyDates.containsAll(List.of(
                LocalDate.of(2025, 3, 10), LocalDate.of(2025, 3, 17), LocalDate.of(2025, 3, 24)
        )));

        List<LocalDate> singleDates = result.stream()
                .filter(s -> s.getId() > 1L) // ID 2 и 3
                .map(TrainingSession::getStartDate)
                .toList();
        assertEquals(2, singleDates.size());
        assertTrue(singleDates.containsAll(List.of(
                LocalDate.of(2025, 3, 12), LocalDate.of(2025, 3, 14)
        )));
    }
}