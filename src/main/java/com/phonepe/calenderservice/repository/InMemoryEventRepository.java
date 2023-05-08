package com.phonepe.calenderservice.repository;

import com.phonepe.calenderservice.pojo.Event;
import com.phonepe.calenderservice.pojo.User;

import java.util.*;

public class InMemoryEventRepository implements EventRepository {
    private Map<User, List<Event>> events;
    private Map<String, Event> eventsMap;

    public InMemoryEventRepository() {
        this.events = new HashMap<>();
        this.eventsMap = new HashMap<>();
    }

    public void add(Event event) {
        event.setId(UUID.randomUUID().toString());

        List<Event> events = this.events.getOrDefault(event.getOrganizer(), new ArrayList<>());
        events.add(event);
        this.events.put(event.getOrganizer(), events);

        // Adding events for participants as well
        for (User participant : event.getParticipants()) {
            events = this.events.getOrDefault(participant, new ArrayList<>());
            events.add(event);
            this.events.put(participant, events);
        }
        this.eventsMap.put(event.getId(), event);
    }

    public void deleteById(String id) {
        Event e = this.eventsMap.get(id);
        for (Map.Entry<User, List<Event>> events : this.events.entrySet()) {
            events.getValue().remove(e);
        }
        this.eventsMap.remove(e.getId());
    }

    public List<Event> findEventsByUser(User user) {
        return this.events.get(user);
    }
}
