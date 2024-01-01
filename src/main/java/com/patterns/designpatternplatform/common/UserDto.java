package com.patterns.designpatternplatform.common;

import java.util.List;

public class UserDto {
    Long id;
    String username;
    String mail;
    String password;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public UserDto(Long id, String username, String mail, String password) {
        this.id = id;
        this.username = username;
        this.mail = mail;
        this.password = password;
    }
}
