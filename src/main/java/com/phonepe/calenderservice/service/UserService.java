package com.phonepe.calenderservice.service;

import com.phonepe.calenderservice.exceptions.UserAlreadyExistsException;
import com.phonepe.calenderservice.exceptions.UserNotFoundException;
import com.phonepe.calenderservice.pojo.User;

public interface UserService {
    User add(User user) throws UserAlreadyExistsException;

    User getByEmailId(String emailId) throws UserNotFoundException;

    void deleteById(String id) throws UserNotFoundException;
}
