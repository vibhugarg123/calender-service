package com.vibhugarg123.calenderservice.service;

import com.vibhugarg123.calenderservice.exceptions.EventConflictingException;
import com.vibhugarg123.calenderservice.exceptions.UserNotFoundException;
import com.vibhugarg123.calenderservice.pojo.Event;
import com.vibhugarg123.calenderservice.pojo.User;

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
