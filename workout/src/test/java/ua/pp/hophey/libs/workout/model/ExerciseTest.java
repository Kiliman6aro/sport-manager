package ua.pp.hophey.libs.workout.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ua.pp.hophey.libs.workout.event.EventBus;
import ua.pp.hophey.libs.workout.event.WorkoutEvent;
import ua.pp.hophey.libs.workout.event.sets.SetStartedEvent;
import ua.pp.hophey.libs.workout.event.sets.SetFinishedEvent;
import ua.pp.hophey.libs.workout.event.workout.WorkoutStartedEvent;
import ua.pp.hophey.libs.workout.event.workout.WorkoutFinishedEvent;
import ua.pp.hophey.libs.workout.event.workout.RestTickEvent;
import ua.pp.hophey.libs.workout.event.exercise.ExerciseStartedEvent;
import ua.pp.hophey.libs.workout.event.exercise.ExerciseFinishedEvent;
import ua.pp.hophey.libs.workout.event.exercise.ExerciseTickEvent;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ExerciseTest {

    private EventBus originalEventBus;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        // Получаем доступ к полю INSTANCE
        Field instanceField = EventBus.class.getDeclaredField("INSTANCE");
        instanceField.setAccessible(true);
        originalEventBus = (EventBus) instanceField.get(null);

        // Подменяем на мок
        EventBus mockEventBus = Mockito.mock(EventBus.class);
        instanceField.set(null, mockEventBus);
    }

    @AfterEach
    void tearDown() throws NoSuchFieldException, IllegalAccessException {
        // Восстанавливаем оригинальный INSTANCE
        Field instanceField = EventBus.class.getDeclaredField("INSTANCE");
        instanceField.setAccessible(true);
        instanceField.set(null, originalEventBus);
    }

    @Test
    void testRunEvents() throws InterruptedException {
        // Создаём шпион для Exercise, чтобы подменить sleep
        Exercise exercise = Mockito.spy(new Exercise("Приседания", 2, 3, 2, 2));
        doNothing().when(exercise).sleep(anyLong()); // Убираем реальный sleep

        // Запускаем run() синхронно
        exercise.run();

        // Проверяем вызовы на моке EventBus
        EventBus mockEventBus = EventBus.getInstance();
        ArgumentCaptor<WorkoutEvent> eventCaptor = ArgumentCaptor.forClass(WorkoutEvent.class);
        verify(mockEventBus, atLeastOnce()).post(eventCaptor.capture());
        List<WorkoutEvent> events = eventCaptor.getAllValues();

        // Считаем события
        long workoutStartedCount = events.stream().filter(e -> e instanceof WorkoutStartedEvent).count();
        long setStartedCount = events.stream().filter(e -> e instanceof SetStartedEvent).count();
        long exerciseStartedCount = events.stream().filter(e -> e instanceof ExerciseStartedEvent).count();
        long exerciseTickCount = events.stream().filter(e -> e instanceof ExerciseTickEvent).count();
        long exerciseFinishedCount = events.stream().filter(e -> e instanceof ExerciseFinishedEvent).count();
        long setFinishedCount = events.stream().filter(e -> e instanceof SetFinishedEvent).count();
        long restTickCount = events.stream().filter(e -> e instanceof RestTickEvent).count();
        long workoutFinishedCount = events.stream().filter(e -> e instanceof WorkoutFinishedEvent).count();

        // Проверки
        assertEquals(1, workoutStartedCount, "Должно быть 1 WorkoutStartedEvent");
        assertEquals(2, setStartedCount, "Должно быть 2 SetStartedEvent");
        assertEquals(6, exerciseStartedCount, "Должно быть 6 ExerciseStartedEvent");
        assertEquals(6, exerciseTickCount, "Должно быть 6 ExerciseTickEvent");
        assertEquals(6, exerciseFinishedCount, "Должно быть 6 ExerciseFinishedEvent");
        assertEquals(2, setFinishedCount, "Должно быть 2 SetFinishedEvent");
        assertEquals(2, restTickCount, "Должно быть 2 RestTickEvent");
        assertEquals(1, workoutFinishedCount, "Должно быть 1 WorkoutFinishedEvent");
    }
}