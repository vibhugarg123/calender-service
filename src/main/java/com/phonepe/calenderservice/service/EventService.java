package com.phonepe.calenderservice.service;

import com.phonepe.calenderservice.exceptions.EventConflictingException;
import com.phonepe.calenderservice.exceptions.UserNotFoundException;
import com.phonepe.calenderservice.pojo.Event;
import com.phonepe.calenderservice.pojo.User;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface EventService {
    Event addNewEvent(String organizerId, String description, List<String> participantIds,
                      Date date, LocalTime startTime, LocalTime endTime) throws UserNotFoundException, EventConflictingException;

    List<Event> getEventsForUser(String userId) throws UserNotFoundException;

    List<Event> getConflictingEventsForUserAndDate(String userId, Date date) throws UserNotFoundException;

    List<LocalDateTime> findEmptySlots(List<User> users, Duration duration);
}
