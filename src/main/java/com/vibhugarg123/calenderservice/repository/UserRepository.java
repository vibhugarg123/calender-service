package com.vibhugarg123.calenderservice.repository;

import com.vibhugarg123.calenderservice.pojo.User;

public interface UserRepository {
    void add(User user);

    User findByEmailId(String emailId);

    User findById(String id);

    void deleteById(String id);
}
