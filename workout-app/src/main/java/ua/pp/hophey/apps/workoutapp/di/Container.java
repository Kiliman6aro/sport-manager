package ua.pp.hophey.apps.workoutapp.di;

import javafx.stage.Stage;
import ua.pp.hophey.apps.workoutapp.handlers.ExitHandler;

import java.util.HashMap;
import java.util.Map;

public class Container {
    private static Container instance;
    private final Map<Class<?>, Object> services = new HashMap<>();

    private Container() {}

    public static synchronized Container getInstance() {
        if (instance == null) {
            instance = new Container();
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> serviceClass) {
        T service = (T) services.get(serviceClass);
        if (service == null) {
            throw new IllegalArgumentException("Service " + serviceClass.getName() + " not registered");
        }
        return service;
    }

    public <T> void setService(Class<T> serviceClass, T service) {
        services.put(serviceClass, service);
    }
}