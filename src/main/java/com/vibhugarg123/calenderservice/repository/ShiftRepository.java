package com.vibhugarg123.calenderservice.repository;

import com.vibhugarg123.calenderservice.pojo.Shift;
import com.vibhugarg123.calenderservice.pojo.User;

import java.util.Date;
import java.util.List;

public interface ShiftRepository {
    Shift add(User user, Shift shift);

    List<Shift> findByUserAndDate(User user, Date date);
}
