### Calender-Service

- Users can enter their shift timings (working hours) or create busy slots. It’s possible for users to have more than one shift in a day.
- Users can fetch events of themselves and other users.
- Users can create events with other users for a defined start time and end time and delete events created by themselves.
- Organizer of an event can fetch the most favorable upcoming empty slot for a given set of users and a particular duration.
  For instance user A would like to check the favorable slot with user B, user C to set an event of 4 hours duration. This would require user A to check the upcoming events in his/her calendar as well as for user B, user C.
- Users can fetch events where they have conflicts for themselves for a particular day.

## Assumptions
- For simplicity, Events are starting on same day and ending on same day.
- Assuming maximum empty slots that can be found by an organizer is 3, that's a defined constant.

## Example

### Instantiation of repositories and services
```
        UserRepository userRepository = new InMemoryUserRepository();
        ShiftRepository shiftRepository = new InMemoryShiftRepository();
        EventRepository eventRepository = new InMemoryEventRepository();

        UserService userService = new UserServiceImpl(userRepository);
        ShiftService shiftService = new ShiftServiceImpl(userRepository, shiftRepository);
        EventService eventService = new EventServiceImpl(userRepository, eventRepository);
```
### Creating a User
```
        // Creating user
        User user1 = new User("vibhu", "g.vibhu05@gmail.com");
        user1 = userService.add(user1);

        User user2 = new User("arun", "arun.aux@gmail.com");
        user2 = userService.add(user2);
        // Creating user
```
### Creating Shifts for a User (More than one shift are allowed for a user)
```
        Date date = new Date();
        // Add shifts for user1
        Shift shift1ForUser1 = new Shift(date, LocalTime.of(9, 0, 0), LocalTime.of(17, 0, 0));
        shift1ForUser1 = shiftService.addShiftForUser(user1.getId(), shift1ForUser1);

        Shift shift2ForUser1 = new Shift(date, LocalTime.of(18, 0, 0), LocalTime.of(19, 0, 0));
        shift2ForUser1 = shiftService.addShiftForUser(user1.getId(), shift2ForUser1);

        // Add shifts for user2
        Shift shiftForUser2 = new Shift(date, LocalTime.of(10, 0, 0), LocalTime.of(18, 0, 0));
        shiftForUser2 = shiftService.addShiftForUser(user2.getId(), shiftForUser2);
```
###  Creating events for a user
```
        List<String> participants = new ArrayList<>();
        // event e1 where user1 is organizer
        participants.add(user2.getId());
        Event e1 = eventService.addNewEvent(user1.getId(), "discussion-1 on calender-service", participants, date, LocalTime.of(10, 0, 0), LocalTime.of(11, 0, 0));
        
        participants.clear();
        participants.add(user1.getId());
        Event e2 = eventService.addNewEvent(user2.getId(), "discussion-2 on calender-service", participants, date, LocalTime.of(10, 0, 0), LocalTime.of(10, 30, 0));
    
```
### Fetching all events for a user
```
        System.out.println(eventService.getEventsForUser(user1.getId()));
        System.out.println(eventService.getEventsForUser(user2.getId()));
```

### Fetch Conflicting Events
        List<Event> conflictedEventsForUser1 = eventService.getConflictingEventsForUserAndDate(user1.getId(), date);
        System.out.println("conflicted events for user1");
        for (Event e : conflictedEventsForUser1) {
            System.out.println(e);
        }
```

### Find empty slots for a User
```
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        List<LocalDateTime> emptySlots = eventService.findEmptySlots(users, Duration.ofHours(2));
        if (null != emptySlots && emptySlots.size() > 0) {
            for (LocalDateTime slot : emptySlots) {
                System.out.println("slot " + slot);
            }
        }
```