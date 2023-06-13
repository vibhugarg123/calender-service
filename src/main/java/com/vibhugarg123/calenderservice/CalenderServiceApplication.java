package com.vibhugarg123.calenderservice;

import com.vibhugarg123.calenderservice.exceptions.EventConflictingException;
import com.vibhugarg123.calenderservice.exceptions.ShiftConflictingException;
import com.vibhugarg123.calenderservice.exceptions.UserAlreadyExistsException;
import com.vibhugarg123.calenderservice.exceptions.UserNotFoundException;
import com.vibhugarg123.calenderservice.pojo.Event;
import com.vibhugarg123.calenderservice.pojo.Shift;
import com.vibhugarg123.calenderservice.pojo.User;
import com.vibhugarg123.calenderservice.repository.*;
import com.vibhugarg123.calenderservice.service.EventService;
import com.vibhugarg123.calenderservice.service.ShiftService;
import com.vibhugarg123.calenderservice.service.UserService;
import com.vibhugarg123.calenderservice.service.impl.EventServiceImpl;
import com.vibhugarg123.calenderservice.service.impl.ShiftServiceImpl;
import com.vibhugarg123.calenderservice.service.impl.UserServiceImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class CalenderServiceApplication {

    public static void main(String[] args) throws UserAlreadyExistsException, UserNotFoundException, ShiftConflictingException, EventConflictingException {
        UserRepository userRepository = new InMemoryUserRepository();
        ShiftRepository shiftRepository = new InMemoryShiftRepository();
        EventRepository eventRepository = new InMemoryEventRepository();

        UserService userService = new UserServiceImpl(userRepository);
        ShiftService shiftService = new ShiftServiceImpl(userRepository, shiftRepository);
        EventService eventService = new EventServiceImpl(userRepository, eventRepository);

        // Creating user
        User user1 = new User("vibhu", "g.vibhu05@gmail.com");
        user1 = userService.add(user1);
        System.out.println("user 1 added" + user1);

        User user2 = new User("arun", "arun.aux@gmail.com");
        user2 = userService.add(user2);
        System.out.println("user 2 added" + user2);
        // Creating user

        // Creating Shifts for user
        Date date = new Date();
        // Add shifts for user1
        Shift shift1ForUser1 = new Shift(date, LocalTime.of(9, 0, 0), LocalTime.of(17, 0, 0));
        shift1ForUser1 = shiftService.addShiftForUser(user1.getId(), shift1ForUser1);
        System.out.println(" shift 1 for user1 is " + shift1ForUser1 + " on date " + date);

        Shift shift2ForUser1 = new Shift(date, LocalTime.of(18, 0, 0), LocalTime.of(19, 0, 0));
        shift2ForUser1 = shiftService.addShiftForUser(user1.getId(), shift2ForUser1);
        System.out.println(" shift 2 for user1 is " + shift2ForUser1 + " on date " + date);

        // Add shifts for user2
        Shift shiftForUser2 = new Shift(date, LocalTime.of(10, 0, 0), LocalTime.of(18, 0, 0));
        shiftForUser2 = shiftService.addShiftForUser(user2.getId(), shiftForUser2);
        System.out.println(" shift for user2 is " + shiftForUser2 + " on date " + date);
        // Creating Shifts for user

        // Creating events
        List<String> participants = new ArrayList<>();
        // event e1 where user1 is organizer
        participants.add(user2.getId());
        Event e1 = eventService.addNewEvent(user1.getId(), "discussion-1 on calender-service", participants, date, LocalTime.of(10, 0, 0), LocalTime.of(11, 0, 0));
        System.out.println("event 1 created " + e1 + " for user " + user1);
        // fetch all events for user1
        System.out.println(eventService.getEventsForUser(user1.getId()));
        // fetch all events for user2
        System.out.println(eventService.getEventsForUser(user2.getId()));

        participants.clear();
        participants.add(user1.getId());
        Event e2 = eventService.addNewEvent(user2.getId(), "discussion-2 on calender-service", participants, date, LocalTime.of(10, 0, 0), LocalTime.of(10, 30, 0));
        System.out.println("event 2 created " + e2 + " for user " + user2);
        // fetch all events for user1
        System.out.println(eventService.getEventsForUser(user1.getId()));
        // fetch all events for user2
        System.out.println(eventService.getEventsForUser(user2.getId()));

        // fetch conflicting events
        List<Event> conflictedEventsForUser1 = eventService.getConflictingEventsForUserAndDate(user1.getId(), date);
        System.out.println("conflicted events for user1");
        for (Event e : conflictedEventsForUser1) {
            System.out.println(e);
        }

        //Find empty slots for user 1 and user2
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        List<LocalDateTime> emptySlots = eventService.findEmptySlots(users, Duration.ofHours(2));
        if (null != emptySlots && emptySlots.size() > 0) {
            for (LocalDateTime slot : emptySlots) {
                System.out.println("slot " + slot);
            }
        }
    }
}
