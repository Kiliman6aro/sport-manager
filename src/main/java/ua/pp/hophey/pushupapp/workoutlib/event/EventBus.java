package ua.pp.hophey.pushupapp.workoutlib.event;

import java.util.ArrayList;
import java.util.List;

public class EventBus {
    private static final EventBus INSTANCE = new EventBus();
    private final List<EventListener> listeners = new ArrayList<>();

    private EventBus() {
        // Singleton
    }

    public static EventBus getInstance() {
        return INSTANCE;
    }

    public void subscribe(EventListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public void unsubscribe(EventListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    public void post(String message) {
        synchronized (listeners) {
            for (EventListener listener : listeners) {
                listener.onEvent(message);
            }
        }
    }
}
