package com.phonepe.calenderservice.repository;

import com.phonepe.calenderservice.pojo.User;

public interface UserRepository {
    void add(User user);

    User findByEmailId(String emailId);

    User findById(String id);

    void deleteById(String id);
}
