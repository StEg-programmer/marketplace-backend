package org.example.marketplacebackend.domain.events;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public final class EventBus {
    private static final EventBus instance = new EventBus();
    private final Map<String, List<EventListener<?>>> listeners = new ConcurrentHashMap<>();

    private EventBus() {}

    public static EventBus getInstance() {
        return instance;
    }

    public <E extends Event> void subscribe(String eventType, EventListener<E> listener) {
        listeners.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>()).add(listener);
    }

    @SuppressWarnings("unchecked")
    public <E extends Event> void publish( E event) {
        List<EventListener<?>> eventListeners = listeners.getOrDefault(event.type(), List.of());
        for (EventListener<?> listener : eventListeners) {
            ((EventListener<E>) listener).onEvent(event);
        }
    }
}
