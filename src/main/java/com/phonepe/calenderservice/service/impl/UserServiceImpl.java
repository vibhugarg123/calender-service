package com.phonepe.calenderservice.service.impl;

import com.phonepe.calenderservice.exceptions.UserAlreadyExistsException;
import com.phonepe.calenderservice.exceptions.UserNotFoundException;
import com.phonepe.calenderservice.pojo.User;
import com.phonepe.calenderservice.repository.UserRepository;
import com.phonepe.calenderservice.service.UserService;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User add(User user) throws UserAlreadyExistsException {
        if (null == this.userRepository.findByEmailId(user.getEmailId())) {
            this.userRepository.add(user);
            return user;
        }
        throw new UserAlreadyExistsException();
    }

    @Override
    public User getByEmailId(String emailId) throws UserNotFoundException {
        User user = this.userRepository.findByEmailId(emailId);
        if (null != user) return user;
        throw new UserNotFoundException();
    }

    @Override
    public void deleteById(String id) throws UserNotFoundException {
        if (this.userRepository.findById(id) == null) {
            throw new UserNotFoundException();
        }
        this.userRepository.deleteById(id);
    }
}
