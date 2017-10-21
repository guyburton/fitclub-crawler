
package com.guyburton.fitclub.domain;

import java.time.LocalDateTime;

public class User {

    private final String userName;

    private final LocalDateTime firstPost;

    public User(String userName, LocalDateTime firstPost) {
        this.userName = userName;
        this.firstPost = firstPost;
    }

    public String getUserName() {
        return userName;
    }
}
