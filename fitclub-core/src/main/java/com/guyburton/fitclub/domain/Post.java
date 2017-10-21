package com.guyburton.fitclub.domain;

public class Post {

    private final User user;

    private final String message;

    private final FitClubWeek fitClubWeek;

    public Post(User user, String message, FitClubWeek fitClubWeek) {
        this.user = user;
        this.message = message;
        this.fitClubWeek = fitClubWeek;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public FitClubWeek getFitClubWeek() {
        return fitClubWeek;
    }
}
