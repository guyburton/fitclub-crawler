package com.guyburton.fitclub.viewmodel;

import java.time.LocalDate;

public class PostViewModel {
    private String message;
    private String url;
    private LocalDate date;
    private Integer week;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getWeek() {
        return week;
    }
}
