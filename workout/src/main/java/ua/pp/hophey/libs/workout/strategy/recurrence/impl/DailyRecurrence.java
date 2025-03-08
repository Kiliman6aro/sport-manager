package ua.pp.hophey.libs.workout.strategy.recurrence.impl;

import ua.pp.hophey.libs.workout.strategy.recurrence.RecurrenceRule;

import java.time.LocalDate;

public class DailyRecurrence implements RecurrenceRule {
    /**
     * Каждый день после начальной даты
     */
    @Override
    public boolean matches(LocalDate date, LocalDate originalDate) {
        return !date.isBefore(originalDate);
    }
}
