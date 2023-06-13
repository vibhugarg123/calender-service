package com.vibhugarg123.calenderservice.service.impl;

import com.vibhugarg123.calenderservice.exceptions.EventConflictingException;
import com.vibhugarg123.calenderservice.exceptions.UserNotFoundException;
import com.vibhugarg123.calenderservice.pojo.Event;
import com.vibhugarg123.calenderservice.pojo.User;
import com.vibhugarg123.calenderservice.repository.EventRepository;
import com.vibhugarg123.calenderservice.repository.UserRepository;
import com.vibhugarg123.calenderservice.service.EventService;
import com.vibhugarg123.calenderservice.utils.DateTimeUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class EventServiceImpl implements EventService {
    UserRepository userRepository;
    EventRepository eventRepository;

    private static final int MAX_SLOTS_TO_FIND = 3;

    public EventServiceImpl(UserRepository userRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public Event addNewEvent(String organizerId, String description, List<String> participantIds,
                             Date date, LocalTime startTime, LocalTime endTime) throws UserNotFoundException, EventConflictingException {
        User organiser = this.userRepository.findById(organizerId);
        if (null == organiser)
            throw new UserNotFoundException();
        List<User> participants = new ArrayList<>();

        for (String id : participantIds) {
            User participant = this.userRepository.findById(id);
            if (null == participant) throw new UserNotFoundException();
            participants.add(participant);
        }

        Event e = new Event(organiser, description, date, startTime, endTime, participants);
        this.eventRepository.add(e);

        return e;
    }

    @Override
    public List<Event> getEventsForUser(String userId) throws UserNotFoundException {
        User user = this.userRepository.findById(userId);
        if (null == user)
            throw new UserNotFoundException();
        return this.eventRepository.findEventsByUser(user);
    }

    @Override
    public List<Event> getConflictingEventsForUserAndDate(String userId, Date date) throws UserNotFoundException {
        User user = this.userRepository.findById(userId);
        if (null == user)
            throw new UserNotFoundException();
        List<Event> events = this.eventRepository.findEventsByUser(user).stream().filter(event -> event.getDate().equals(date)).toList();

        Set<Event> conflictingEvents = new HashSet<>();
        for (int i = 0; i < events.size(); i++) {
            for (int j = i + 1; j < events.size(); j++) {
                if (events.get(i).isConflict(events.get(j))) {
                    conflictingEvents.add(events.get(i));
                    conflictingEvents.add(events.get(j));
                }
            }
        }

        return conflictingEvents.stream().toList();
    }

    public List<LocalDateTime> findEmptySlots(List<User> users, Duration duration) {
        // Step 1
        List<Event> allEvents = new ArrayList<>();
        for (User user : users) {
            List<Event> eventsForUser = eventRepository.findEventsByUser(user);
            if (null != eventsForUser && eventsForUser.size() > 0) {
                allEvents.addAll(eventsForUser);
            }
        }

        // Step 2: Sort the list of events by their start times in ascending order.
        allEvents.sort(Comparator.comparing(Event::getStartTime));

        List<LocalDateTime> emptySlots = new ArrayList<>();
        LocalDateTime endOfLastEvent = null;
        for (Event event : allEvents) {
            LocalDateTime startOfNextEvent = event.getStartTime().atDate(LocalDate.from(DateTimeUtils.convertToLocalDate(event.getDate()).atTime(event.getEndTime())));
            if (endOfLastEvent != null) {
                Duration timeBetweenEvents = Duration.between(endOfLastEvent, startOfNextEvent);
                if (timeBetweenEvents.compareTo(duration) >= 0) {
                    emptySlots.add(endOfLastEvent);
                    if (emptySlots.size() == MAX_SLOTS_TO_FIND) {
                        break;
                    }
                }
            }
            endOfLastEvent = event.getEndTime().atDate(DateTimeUtils.convertToLocalDate(event.getDate()));
        }
        if (endOfLastEvent != null) {
            LocalDateTime endOfDay = endOfLastEvent.toLocalDate().atTime(LocalTime.MAX);
            Duration timeToEndOfDay = Duration.between(endOfLastEvent, endOfDay);
            if (timeToEndOfDay.compareTo(duration) >= 0) {
                emptySlots.add(endOfLastEvent);
            }
        }

        return emptySlots;
    }
}
