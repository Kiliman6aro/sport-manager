package ua.pp.hophey.libs.workout.strategy.recurrence.impl;

import ua.pp.hophey.libs.workout.strategy.recurrence.RecurrenceRule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DayOfWeekRecurrence implements RecurrenceRule {
    private final DayOfWeek dayOfWeek;
    private final int interval; // Каждые N недель

    public DayOfWeekRecurrence(DayOfWeek dayOfWeek, int interval) {
        this.dayOfWeek = dayOfWeek;
        this.interval = interval;
    }

    @Override
    public boolean matches(LocalDate date, LocalDate originalDate) {
        long daysBetween = ChronoUnit.DAYS.between(originalDate, date);
        return date.getDayOfWeek() == dayOfWeek && daysBetween >= 0 && daysBetween % (7 * interval) == 0;
    }

    public DayOfWeek getDayOfWeek() { return dayOfWeek; }
    public int getInterval() { return interval; }
}