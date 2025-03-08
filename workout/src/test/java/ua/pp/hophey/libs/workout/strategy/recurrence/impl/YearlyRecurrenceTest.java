package ua.pp.hophey.libs.workout.strategy.recurrence.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class YearlyRecurrenceTest {

    private YearlyRecurrence recurrence;

    @BeforeEach
    void setUp() {
        recurrence = new YearlyRecurrence();
    }

    @Test
    void testMatches_SameDay() {
        LocalDate originalDate = LocalDate.of(2024, 3, 4);
        assertTrue(recurrence.matches(originalDate, originalDate));
    }

    @Test
    void testMatches_SameDateDifferentYear() {
        LocalDate originalDate = LocalDate.of(2020, 3, 4);
        LocalDate testDate = originalDate.plusYears(4);
        assertTrue(recurrence.matches(testDate, originalDate));
    }

    @Test
    void testMatches_DifferentDaySameMonth() {
        LocalDate originalDate = LocalDate.of(2024, 3, 4);
        LocalDate testDate = LocalDate.of(2025, 3, 5);
        assertFalse(recurrence.matches(testDate, originalDate));
    }

    @Test
    void testMatches_DifferentMonthSameDay() {
        LocalDate originalDate = LocalDate.of(2024, 3, 4);
        LocalDate testDate = LocalDate.of(2025, 4, 4);
        assertFalse(recurrence.matches(testDate, originalDate));
    }

    @Test
    void testMatches_LeapYear() {
        LocalDate originalDate = LocalDate.of(2020, 2, 29);
        LocalDate testDate = LocalDate.of(2024, 2, 29);
        assertTrue(recurrence.matches(testDate, originalDate));
    }

    @Test
    void testMatches_LeapYear_NonLeapYear() {
        LocalDate originalDate = LocalDate.of(2020, 2, 29);
        LocalDate testDate = LocalDate.of(2021, 2, 28);
        assertFalse(recurrence.matches(testDate, originalDate));
    }
}
