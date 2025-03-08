package ua.pp.hophey.libs.workout.strategy.recurrence.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class MonthlyRecurrenceTest {
    private MonthlyRecurrence monthly;
    private MonthlyRecurrence everyTwoMonths;

    @BeforeEach
    void setUp() {
        monthly = new MonthlyRecurrence(1); // Каждый месяц
        everyTwoMonths = new MonthlyRecurrence(2); // Каждые 2 месяца
    }

    @Test
    void testMatches_SameDay() {
        LocalDate originalDate = LocalDate.of(2025, 3, 10);
        assertTrue(monthly.matches(originalDate, originalDate));
    }

    @Test
    void testMatches_NextMonth() {
        LocalDate originalDate = LocalDate.of(2025, 3, 10);
        LocalDate testDate = LocalDate.of(2025, 4, 10);
        assertTrue(monthly.matches(testDate, originalDate));
    }

    @Test
    void testMatches_EveryTwoMonths() {
        LocalDate originalDate = LocalDate.of(2025, 3, 10);
        LocalDate testDate = LocalDate.of(2025, 5, 10);
        assertTrue(everyTwoMonths.matches(testDate, originalDate));
    }

    @Test
    void testMatches_WrongDay() {
        LocalDate originalDate = LocalDate.of(2025, 3, 10);
        LocalDate testDate = LocalDate.of(2025, 4, 11);
        assertFalse(monthly.matches(testDate, originalDate));
    }

    @Test
    void testMatches_WrongInterval() {
        LocalDate originalDate = LocalDate.of(2025, 3, 10);
        LocalDate testDate = LocalDate.of(2025, 4, 10);
        assertFalse(everyTwoMonths.matches(testDate, originalDate));
    }

    @Test
    void testMatches_BeforeOriginalDate() {
        LocalDate originalDate = LocalDate.of(2025, 3, 10);
        LocalDate testDate = LocalDate.of(2025, 2, 10);
        assertFalse(monthly.matches(testDate, originalDate));
    }

    @Test
    void testMatches_EdgeCase_February() {
        LocalDate originalDate = LocalDate.of(2025, 1, 31);
        LocalDate testDate = LocalDate.of(2025, 2, 28); // Февраль короче
        assertFalse(monthly.matches(testDate, originalDate));
    }
}
