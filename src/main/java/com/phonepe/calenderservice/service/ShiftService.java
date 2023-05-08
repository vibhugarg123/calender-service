package com.phonepe.calenderservice.service;

import com.phonepe.calenderservice.exceptions.ShiftConflictingException;
import com.phonepe.calenderservice.exceptions.UserNotFoundException;
import com.phonepe.calenderservice.pojo.Shift;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

public interface ShiftService {
    Shift addShiftForUser(String userId, Shift shift) throws UserNotFoundException, ShiftConflictingException;

    List<Shift> getShiftsForUserAndDate(String userId, Date date) throws UserNotFoundException;
}

