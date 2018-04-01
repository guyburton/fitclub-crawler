package com.guyburton.fitclub.viewmodel;

import com.google.common.collect.Ordering;
import com.guyburton.fitclub.store.entities.JpaPost;
import com.guyburton.fitclub.store.entities.JpaUser;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class UserViewModel {

    public static final Ordering<JpaPost> ORDERING =
        Ordering.natural().onResultOf(p -> p.getWeek().getPostDate());
    private final JpaUser user;
    private final List<JpaPost> posts;

    public UserViewModel(JpaUser user, List<JpaPost> posts) {
        this.user = user;
        this.posts = posts.stream()
            .sorted(ORDERING.reverse())
            .collect(Collectors.toList());
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
