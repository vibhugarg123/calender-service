package com.vibhugarg123.calenderservice.repository;

import com.vibhugarg123.calenderservice.pojo.Event;
import com.vibhugarg123.calenderservice.pojo.User;

import java.util.List;

public interface EventRepository {
    void add(Event event);

    void deleteById(String id);

    List<Event> findEventsByUser(User user);
}
