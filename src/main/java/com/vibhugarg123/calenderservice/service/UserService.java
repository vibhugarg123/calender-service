package com.vibhugarg123.calenderservice.service;

import com.vibhugarg123.calenderservice.exceptions.UserAlreadyExistsException;
import com.vibhugarg123.calenderservice.exceptions.UserNotFoundException;
import com.vibhugarg123.calenderservice.pojo.User;

public interface UserService {
    User add(User user) throws UserAlreadyExistsException;

    User getByEmailId(String emailId) throws UserNotFoundException;

    void deleteById(String id) throws UserNotFoundException;
}
