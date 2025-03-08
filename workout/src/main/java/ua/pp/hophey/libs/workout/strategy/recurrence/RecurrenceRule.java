package ua.pp.hophey.libs.workout.strategy.recurrence;

import java.time.LocalDate;

public interface RecurrenceRule {
    boolean matches(LocalDate date, LocalDate originalDate);
}
