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
            LocalDate sessionStartDate = session.getStartDate();
            RecurrenceRule rule = session.getRecurrenceRule();

            // Если нет правила повторения, проверяем, попадает ли начальная дата в диапазон
            if (rule == null) {
                if (!sessionStartDate.isBefore(startDate) && !sessionStartDate.isAfter(endDate)) {
                    result.add(session);
                }
                continue;
            }

            // Для сессий с повторением: генерируем все даты, начиная с sessionStartDate,
            // но включаем только те, что попадают в [startDate, endDate]
            LocalDate currentDate = sessionStartDate;

            while (!currentDate.isAfter(endDate)) {
                if (!currentDate.isBefore(startDate) && rule.matches(currentDate, sessionStartDate)) {
                    // Создаём новую сессию с текущей датой
                    TrainingSession repeatedSession = new TrainingSession(
                            session.getId(),
                            currentDate,
                            session.getStartTime(),
                            session.getName()
                    );
                    repeatedSession.setRecurrenceRule(rule);
                    result.add(repeatedSession);
                }
                currentDate = currentDate.plusDays(1); // Предполагаем ежедневное увеличение, можно адаптировать под правило
            }
        }
        return result;
    }
}