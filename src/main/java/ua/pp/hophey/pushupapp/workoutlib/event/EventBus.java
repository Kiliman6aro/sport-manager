package ua.pp.hophey.pushupapp.workoutlib.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EventBus {
    private static final EventBus INSTANCE = new EventBus();
    private final Map<Class<? extends WorkoutEvent>, List<Consumer<? extends WorkoutEvent>>> handlers = new HashMap<>();

    private EventBus() {}

    public static EventBus getInstance() {
        return INSTANCE;
    }

    public <T extends WorkoutEvent> void subscribe(Class<T> eventType, Consumer<T> handler) {
        synchronized (handlers) {
            handlers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(handler);
        }
    }

    public void unsubscribe(Class<? extends WorkoutEvent> eventType, Consumer<? extends WorkoutEvent> handler) {
        synchronized (handlers) {
            List<Consumer<? extends WorkoutEvent>> eventHandlers = handlers.get(eventType);
            if (eventHandlers != null) {
                eventHandlers.remove(handler);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void post(WorkoutEvent event) {
        synchronized (handlers) {
            List<Consumer<? extends WorkoutEvent>> eventHandlers = handlers.get(event.getClass());
            if (eventHandlers != null) {
                for (Consumer<? extends WorkoutEvent> handler : eventHandlers) {
                    ((Consumer<WorkoutEvent>) handler).accept(event);
                }
            }
        }
    }
}
