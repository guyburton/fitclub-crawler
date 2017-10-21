package com.guyburton.fitclub.store.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name="User")
public class JpaUser {

    @Id
    private String username;

    @Column(name = "LastPosted")
    private LocalDate lastPosted;

    @Column(name = "FirstPosted")
    private LocalDate firstPosted;

    public JpaUser(String username, LocalDate lastPosted, LocalDate firstPosted) {
        this.username = username;
        this.lastPosted = lastPosted;
        this.firstPosted = firstPosted;
    }

    public JpaUser() {
    }

    public LocalDate getFirstPosted() {
        return firstPosted;
    }

    public String getUsername() {
        return username;
    }

    public LocalDate getLastPosted() {
        return lastPosted;
    }

    public void setLastPosted(LocalDate lastPosted) {
        this.lastPosted = lastPosted;
    }

    public void setFirstPosted(LocalDate postDate) {
        this.firstPosted = postDate;
    }

    @Override
    public String toString() {
        return "JpaUser{" +
            "username='" + username + '\'' +
            '}';
    }
}
