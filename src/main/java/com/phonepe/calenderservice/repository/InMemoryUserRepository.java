package com.phonepe.calenderservice.repository;

import com.phonepe.calenderservice.pojo.User;
import lombok.Data;

import java.util.*;

@Data
public class InMemoryUserRepository implements UserRepository {
    private Map<String, User> users;

    public InMemoryUserRepository() {
        this.users = new HashMap<>();
    }

    public void add(User user) {
        String uuid = UUID.randomUUID().toString();
        user.setId(uuid);
        this.users.put(uuid, user);
    }

    public User findByEmailId(String emailId) {
        for (Map.Entry<String, User> user : users.entrySet()) {
            if (user.getValue().getEmailId().equals(emailId)) {
                return user.getValue();
            }
        }
        return null;
    }

    public User findById(String id) {
        return this.users.get(id);
    }

    public void deleteById(String id) {
        this.users.remove(id);
    }
}
