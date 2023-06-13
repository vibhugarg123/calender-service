package com.vibhugarg123.calenderservice.service.impl;

import com.vibhugarg123.calenderservice.exceptions.ShiftConflictingException;
import com.vibhugarg123.calenderservice.exceptions.UserNotFoundException;
import com.vibhugarg123.calenderservice.pojo.Shift;
import com.vibhugarg123.calenderservice.pojo.User;
import com.vibhugarg123.calenderservice.repository.ShiftRepository;
import com.vibhugarg123.calenderservice.repository.UserRepository;
import com.vibhugarg123.calenderservice.service.ShiftService;

import java.util.Date;
import java.util.List;

public class ShiftServiceImpl implements ShiftService {
    UserRepository userRepository;
    ShiftRepository shiftRepository;

    public ShiftServiceImpl(UserRepository userRepository, ShiftRepository shiftRepository) {
        this.userRepository = userRepository;
        this.shiftRepository = shiftRepository;
    }

    @Override
    public Shift addShiftForUser(String userId, Shift shift) throws UserNotFoundException, ShiftConflictingException {
        User user = userRepository.findById(userId);
        if (null == user) {
            throw new UserNotFoundException();
        }

        List<Shift> shiftsForUser = shiftRepository.findByUserAndDate(user, shift.getDate());
        if (null != shiftsForUser) {
            for (Shift s : shiftsForUser) {
                if (s.isConflicting(shift)) {
                    throw new ShiftConflictingException();
                }
            }
        }
        this.shiftRepository.add(user, shift);
        return shift;
    }

    @Override
    public List<Shift> getShiftsForUserAndDate(String userId, Date date) throws UserNotFoundException {
        User user = userRepository.findById(userId);
        if (null == user) {
            throw new UserNotFoundException();
        }

        return shiftRepository.findByUserAndDate(user, date);
    }
}
