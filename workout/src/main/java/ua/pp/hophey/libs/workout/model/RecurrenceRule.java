package ua.pp.hophey.libs.workout.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RecurrenceRule {
    private final String type;
    private final int interval;

    public RecurrenceRule(String type, int interval) {
        this.type = type;
        this.interval = interval;
    }

    public boolean matches(LocalDate date, LocalDate originalDate) {
        long daysBetween = ChronoUnit.DAYS.between(originalDate, date);
        if ("daily".equals(type)) {
            return daysBetween % interval == 0;
        }
        if ("weekly".equals(type)) {
            return daysBetween % (7 * interval) == 0;
        }
        if ("monthly".equals(type)) {
            return date.getDayOfMonth() == originalDate.getDayOfMonth();
        }
        return false;
    }
}
