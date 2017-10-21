package com.guyburton.fitclub.viewmodel;

import com.guyburton.fitclub.store.entities.JpaPost;
import com.guyburton.fitclub.store.entities.JpaUser;

import java.time.LocalDate;
import java.util.List;

public class UserViewModel {

    private final JpaUser user;
    private final List<JpaPost> posts;

    public UserViewModel(JpaUser user, List<JpaPost> posts) {
        this.user = user;
        this.posts = posts;
    }

    public String getUsername() {
        return user.getUsername();
    }

    public LocalDate lastPosted() {
        return user.getLastPosted();
    }

    public List<JpaPost> getPosts() {
        return posts;
    }
}
