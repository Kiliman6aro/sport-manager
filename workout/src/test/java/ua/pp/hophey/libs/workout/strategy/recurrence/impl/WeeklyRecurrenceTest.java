package ua.pp.hophey.libs.workout.strategy.recurrence.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WeeklyRecurrenceTest {

    private WeeklyRecurrence recurrence;
    private WeeklyRecurrence weeklyRecurrence;

    @BeforeEach
    void setUp() {
        recurrence = new WeeklyRecurrence(2); // Каждые 2 недели
        weeklyRecurrence = new WeeklyRecurrence(1); // Каждую неделю
    }

    @Test
    void testMatches_SameDay() {
        LocalDate originalDate = LocalDate.of(2024, 3, 4); // Понедельник
        assertTrue(recurrence.matches(originalDate, originalDate));
    }

    @Test
    void testMatches_ValidRecurrence() {
        LocalDate originalDate = LocalDate.of(2024, 3, 4);
        LocalDate testDate = originalDate.plusWeeks(2);
        assertTrue(recurrence.matches(testDate, originalDate));
    }

    @Test
    void testMatches_ValidWeeklyRecurrence() {
        LocalDate originalDate = LocalDate.of(2024, 3, 4);
        LocalDate testDate = originalDate.plusWeeks(1);
        assertTrue(weeklyRecurrence.matches(testDate, originalDate));
    }

    @Test
    void testMatches_InvalidRecurrence_WrongInterval() {
        LocalDate originalDate = LocalDate.of(2024, 3, 4);
        LocalDate testDate = originalDate.plusWeeks(1);
        assertFalse(recurrence.matches(testDate, originalDate));
    }

    @Test
    void testMatches_BeforeOriginalDate() {
        LocalDate originalDate = LocalDate.of(2024, 3, 4);
        LocalDate testDate = originalDate.minusWeeks(1);
        assertFalse(recurrence.matches(testDate, originalDate));
    }
}
