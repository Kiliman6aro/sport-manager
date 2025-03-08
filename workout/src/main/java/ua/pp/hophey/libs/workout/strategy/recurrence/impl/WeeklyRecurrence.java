package ua.pp.hophey.libs.workout.strategy.recurrence.impl;

import ua.pp.hophey.libs.workout.strategy.recurrence.RecurrenceRule;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class WeeklyRecurrence implements RecurrenceRule {

    private final int interval;

    public WeeklyRecurrence(int interval) {
        this.interval = interval;
    }

    @Override
    public boolean matches(LocalDate date, LocalDate originalDate) {
        long daysBetween = ChronoUnit.DAYS.between(originalDate, date);
        return daysBetween >= 0 && daysBetween % (7 * interval) == 0;
    }

    public int getInterval() {
        return interval;
    }
}
