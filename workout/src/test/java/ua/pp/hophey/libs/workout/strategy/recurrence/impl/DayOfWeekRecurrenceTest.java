package ua.pp.hophey.libs.workout.strategy.recurrence.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DayOfWeekRecurrenceTest {

    private DayOfWeekRecurrence recurrence;
    private DayOfWeekRecurrence weeklyRecurrence;

    @BeforeEach
    void setUp() {
        recurrence = new DayOfWeekRecurrence(DayOfWeek.MONDAY, 2); // Каждые 2 недели по понедельникам
        weeklyRecurrence = new DayOfWeekRecurrence(DayOfWeek.MONDAY, 1); // Каждую неделю по понедельникам
    }

    @Test
    void testMatches_SameDay() {
        LocalDate originalDate = LocalDate.of(2024, 3, 4); // Понедельник
        assertTrue(recurrence.matches(originalDate, originalDate));
    }

    @Test
    void testMatches_ValidRecurrence() {
        LocalDate originalDate = LocalDate.of(2024, 3, 4); // Понедельник
        LocalDate testDate = originalDate.plusWeeks(2); // Через 2 недели, тоже понедельник
        assertTrue(recurrence.matches(testDate, originalDate));
    }

    @Test
    void testMatches_ValidWeeklyRecurrence() {
        LocalDate originalDate = LocalDate.of(2024, 3, 4); // Понедельник
        LocalDate testDate = originalDate.plusWeeks(1); // Через 1 неделю, понедельник
        assertTrue(weeklyRecurrence.matches(testDate, originalDate));
    }

    @Test
    void testMatches_InvalidRecurrence_WrongDay() {
        LocalDate originalDate = LocalDate.of(2024, 3, 4); // Понедельник
        LocalDate testDate = originalDate.plusWeeks(2).plusDays(1); // Вторник
        assertFalse(recurrence.matches(testDate, originalDate));
    }

    @Test
    void testMatches_InvalidRecurrence_WrongInterval() {
        LocalDate originalDate = LocalDate.of(2024, 3, 4); // Понедельник
        LocalDate testDate = originalDate.plusWeeks(1); // Через 1 неделю (не 2)
        assertFalse(recurrence.matches(testDate, originalDate));
    }

    @Test
    void testMatches_BeforeOriginalDate() {
        LocalDate originalDate = LocalDate.of(2024, 3, 4);
        LocalDate testDate = originalDate.minusWeeks(2);
        assertFalse(recurrence.matches(testDate, originalDate));
    }
}
