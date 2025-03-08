package ua.pp.hophey.libs.workout.strategy.recurrence.impl;

import ua.pp.hophey.libs.workout.strategy.recurrence.RecurrenceRule;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MonthlyRecurrence implements RecurrenceRule {
    private final int interval; // Каждые N месяцев

    public MonthlyRecurrence(int interval) {
        this.interval = interval;
    }

    @Override
    public boolean matches(LocalDate date, LocalDate originalDate) {
        long monthsBetween = ChronoUnit.MONTHS.between(originalDate, date);
        return monthsBetween >= 0 &&
                monthsBetween % interval == 0 &&
                date.getDayOfMonth() == originalDate.getDayOfMonth();
    }

    public int getInterval() { return interval; }
}