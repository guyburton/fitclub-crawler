package com.guyburton.fitclub.store.entities;

import javax.persistence.*;

@Entity
@Table(name = "Posts")
public class JpaPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Username")
    private String username;

    @Column(name = "Message", length = 20000)
    private String message;

    @ManyToOne(fetch=FetchType.EAGER, optional = false)
    @JoinColumn(name="WeekId")
    private JpaFitClubWeek week;

    protected JpaPost() {

    }

    public JpaPost(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public JpaFitClubWeek getWeek() {
        return week;
    }

    public void setWeek(JpaFitClubWeek week) {
        this.week = week;
    }

}
