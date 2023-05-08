package com.phonepe.calenderservice.repository;

import com.phonepe.calenderservice.pojo.Event;
import com.phonepe.calenderservice.pojo.User;

import java.util.List;

public interface EventRepository {
    void add(Event event);

    void deleteById(String id);

    List<Event> findEventsByUser(User user);
}
