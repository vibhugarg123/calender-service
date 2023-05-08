package com.phonepe.calenderservice.pojo;

import lombok.Data;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
public class Event {
    String id;
    User organizer;
    String description;
    Date date;
    LocalTime startTime;
    LocalTime endTime;
    List<User> participants;

    public Event(User organizer, String description, Date date, LocalTime startTime, LocalTime endTime, List<User> participants) {
        this.organizer = organizer;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.participants = participants;
    }

    public boolean isConflict(Event e) {
        if (!this.getDate().equals(e.getDate())) {
            return false;
        }

        return (this.getStartTime().isBefore(e.getEndTime()) && this.getEndTime().isAfter(e.getStartTime())) ||
                (e.getStartTime().isBefore(this.getEndTime()) && e.getEndTime().isAfter(this.getStartTime()));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Event)) {
            return false;
        }

        Event e = (Event) o;

        return this.getId().equals(e.getId());
    }
}
