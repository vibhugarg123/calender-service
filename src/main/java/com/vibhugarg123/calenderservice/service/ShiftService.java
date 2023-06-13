package com.vibhugarg123.calenderservice.service;

import com.vibhugarg123.calenderservice.exceptions.ShiftConflictingException;
import com.vibhugarg123.calenderservice.exceptions.UserNotFoundException;
import com.vibhugarg123.calenderservice.pojo.Shift;

import java.util.Date;
import java.util.List;

public interface ShiftService {
    Shift addShiftForUser(String userId, Shift shift) throws UserNotFoundException, ShiftConflictingException;

    List<Shift> getShiftsForUserAndDate(String userId, Date date) throws UserNotFoundException;
}

