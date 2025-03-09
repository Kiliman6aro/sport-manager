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


            if (sessionStartDate.isBefore(startDate) || sessionStartDate.isAfter(endDate)) {
                continue;
            }

            //Если нет правила повторения, тонужно убедится,что дата старта попадает в промежуток между датами.
            if (rule == null) {
                if (!sessionStartDate.isBefore(startDate) && !sessionStartDate.isAfter(endDate)) {
                    result.add(session);
                }
                continue;
            }


            // Начинаем с начальной даты сессии или с начала диапазона, если она раньше
            LocalDate currentDate = sessionStartDate.isBefore(startDate) ? startDate : sessionStartDate;

            // Проходим по дням до конца диапазона
            while (!currentDate.isAfter(endDate)) {
                if (rule.matches(currentDate, sessionStartDate)) {
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
                currentDate = currentDate.plusDays(1);
            }
        }
        return result;
    }
}