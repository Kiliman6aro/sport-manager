package ua.pp.hophey.libs.workout.services;

import ua.pp.hophey.libs.workout.model.TrainingSession;
import ua.pp.hophey.libs.workout.strategy.recurrence.RecurrenceRule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrainingSessionService {

    public List<TrainingSession> getSessionsInRange(List<TrainingSession> sessions,
                                                    LocalDate startDate,
                                                    LocalDate endDate) {
        List<TrainingSession> result = new ArrayList<>();

        for (TrainingSession session : sessions) {
            LocalDate currentDate = session.getStartDate();
            RecurrenceRule rule = session.getRecurrenceRule();

            // Если правила нет, добавляем сессию только если она в диапазоне
            if (rule == null) {
                if (!currentDate.isBefore(startDate) && !currentDate.isAfter(endDate)) {
                    result.add(session);
                }
                continue;
            }

            // Генерируем повторения
            while (!currentDate.isAfter(endDate)) {
                if (!currentDate.isBefore(startDate) && rule.matches(currentDate, session.getStartDate())) {
                    // Создаём новую сессию для каждой даты повторения
                    TrainingSession repeatedSession = new TrainingSession(
                            session.getId(),
                            currentDate,
                            session.getStartTime(),
                            session.getName()
                    );
                    repeatedSession.setRecurrenceRule(rule); // Сохраняем правило
                    result.add(repeatedSession);
                }
                currentDate = currentDate.plusDays(1); // Шаг на день
            }
        }

        return result;
    }
}