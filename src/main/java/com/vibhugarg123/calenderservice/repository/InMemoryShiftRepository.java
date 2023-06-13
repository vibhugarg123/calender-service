package com.vibhugarg123.calenderservice.repository;

import com.vibhugarg123.calenderservice.pojo.Shift;
import com.vibhugarg123.calenderservice.pojo.User;

import java.util.*;

public class InMemoryShiftRepository implements ShiftRepository {
    Map<User, Set<Shift>> shiftsMap;

    public InMemoryShiftRepository() {
        this.shiftsMap = new HashMap<>();
    }

    public Shift add(User user, Shift shift) {
        shift.setId(UUID.randomUUID().toString());

        Set<Shift> shiftsForUser = this.shiftsMap.getOrDefault(user, new HashSet<>());
        shiftsForUser.add(shift);
        this.shiftsMap.put(user, shiftsForUser);
        return shift;
    }

    public List<Shift> findByUserAndDate(User user, Date date) {
        List<Shift> shifts = new ArrayList<>();
        Set<Shift> shiftsForRequiredUser = this.shiftsMap.get(user);
        if (null == shiftsForRequiredUser) {
            return null;
        }
        for (Shift s : shiftsForRequiredUser) {
            if (s.getDate().equals(date)) {
                shifts.add(s);
            }
        }
        return shifts;
    }
}
