package com.phonepe.calenderservice.repository;

import com.phonepe.calenderservice.pojo.Shift;
import com.phonepe.calenderservice.pojo.User;

import java.util.Date;
import java.util.List;

public interface ShiftRepository {
    Shift add(User user, Shift shift);

    List<Shift> findByUserAndDate(User user, Date date);
}
