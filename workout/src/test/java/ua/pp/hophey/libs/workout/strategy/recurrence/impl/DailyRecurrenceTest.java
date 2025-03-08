package ua.pp.hophey.libs.workout.strategy.recurrence.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DailyRecurrenceTest {

    private DailyRecurrence dailyRecurrence;

    @BeforeEach
    void setUp() {
        dailyRecurrence = new DailyRecurrence();
    }

    @Test
    void testMatches_SameDay() {
        LocalDate originalDate = LocalDate.of(2024, 3, 8);
        assertTrue(dailyRecurrence.matches(originalDate, originalDate));
    }

    @Test
    void testMatches_AfterOriginalDate() {
        LocalDate originalDate = LocalDate.of(2024, 3, 8);
        LocalDate testDate = originalDate.plusDays(1);
        assertTrue(dailyRecurrence.matches(testDate, originalDate));
    }

    @Test
    void testMatches_BeforeOriginalDate() {
        LocalDate originalDate = LocalDate.of(2024, 3, 8);
        LocalDate testDate = originalDate.minusDays(1);
        assertFalse(dailyRecurrence.matches(testDate, originalDate));
    }
}
