package ua.pp.hophey.libs.workout.model;

import ua.pp.hophey.libs.workout.strategy.recurrence.RecurrenceRule;

import java.time.LocalDate;
import java.time.LocalTime;

public class TrainingSession {

    private long id;
    private LocalDate startDate;
    private LocalTime startTime;
    private String name;
    private RecurrenceRule recurrenceRule;


    public TrainingSession(long id, LocalDate startDate, LocalTime startTime, String name) {
        this.id = id;
        this.startDate = startDate;
        this.startTime = startTime;
        this.name = name;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRecurrenceRule(RecurrenceRule recurrenceRule) {
        this.recurrenceRule = recurrenceRule;
    }

    public RecurrenceRule getRecurrenceRule() {
        return this.recurrenceRule;
    }
}
