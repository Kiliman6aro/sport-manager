package ua.pp.hophey.libs.workout.strategy.recurrence.impl;

import ua.pp.hophey.libs.workout.strategy.recurrence.RecurrenceRule;

import java.time.LocalDate;

public class YearlyRecurrence implements RecurrenceRule {

    @Override
    public boolean matches(LocalDate date, LocalDate originalDate) {
        return date.getDayOfMonth() == originalDate.getDayOfMonth() &&
                date.getMonth() == originalDate.getMonth();
    }
}
