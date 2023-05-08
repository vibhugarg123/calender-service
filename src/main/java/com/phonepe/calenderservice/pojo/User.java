package com.phonepe.calenderservice.pojo;

import lombok.Data;

@Data
public class User {
    private String id;
    private String name;
    private String emailId;

    public User(String name, String emailId) {
        this.name = name;
        this.emailId = emailId;
    }
}
