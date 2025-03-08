package ua.pp.hophey.libs.workout.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class TrainingSession {

    private long id;
    private LocalDate startDate;
    private LocalTime startTime;
    private String name;
    private String recurrenceType;
    private int recurrenceInterval;
    private LocalDate endDate;


    public TrainingSession(long id, LocalDate startDate, LocalTime startTime, String name,
                           String recurrenceType, int recurrenceInterval) {
        this(id, startDate, startTime, name, recurrenceType, recurrenceInterval, null);
    }

    public TrainingSession(long id, LocalDate startDate, LocalTime startTime, String name, String recurrenceType, int recurrenceInterval, LocalDate endDate) {
        this.id = id;
        this.startDate = startDate;
        this.startTime = startTime;
        this.name = name;
        this.recurrenceType = recurrenceType;
        this.recurrenceInterval = recurrenceInterval;
        this.endDate = endDate;
    }

    public long getId() {
        return id;
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

    public String getRecurrenceType() {
        return recurrenceType;
    }

    public void setRecurrenceType(String recurrenceType) {
        this.recurrenceType = recurrenceType;
    }

    public int getRecurrenceInterval() {
        return recurrenceInterval;
    }

    public void setRecurrenceInterval(int recurrenceInterval) {
        this.recurrenceInterval = recurrenceInterval;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }


    public RecurrenceRule getRecurrence() {
        return new RecurrenceRule(recurrenceType, recurrenceInterval);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingSession that = (TrainingSession) o;
        return id == that.id &&
                recurrenceInterval == that.recurrenceInterval &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(name, that.name) &&
                Objects.equals(recurrenceType, that.recurrenceType) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, startTime, name, recurrenceType, recurrenceInterval, endDate);
    }
}
