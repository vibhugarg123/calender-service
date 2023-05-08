package com.phonepe.calenderservice.pojo;

import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Data
public class Shift {
    private String id;
    private Date date;
    private LocalTime startTime;
    private LocalTime endTime;

    public Shift(Date date, LocalTime startTime, LocalTime endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Shift)) {
            return false;
        }

        Shift s = (Shift) o;

        return this.getDate().equals(s.getDate()) &&
                this.getStartTime().equals(s.getStartTime()) &&
                this.getEndTime().equals(s.getEndTime());
    }

    public boolean isConflicting(Shift s) {
        if (!this.getDate().equals(s.getDate())) {
            return false;
        }

        return (this.getStartTime().isBefore(this.getEndTime()) && this.getEndTime().isAfter(s.getStartTime())) ||
                (s.getStartTime().isBefore(this.getEndTime()) && s.getEndTime().isAfter(this.getStartTime()));
    }
}
