package com.guyburton.fitclub.store.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "FitClubWeek")
public class JpaFitClubWeek {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PostDate")
    private LocalDate postDate;

    @Column(name = "PostedBy")
    private String postedBy;

    @Column(name = "URL")
    private String url;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "week", fetch = FetchType.EAGER)
    private List<JpaPost> posts;
    public JpaFitClubWeek() {
    }

    public JpaFitClubWeek(Integer id, LocalDate postDate, String postedBy, String url, List<JpaPost> posts) {
        this.id = id;
        this.postDate = postDate;
        this.postedBy = postedBy;
        this.url = url;
        this.posts = posts;
        posts.forEach(p -> p.setWeek(this));
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public String getUrl() {
        return url;
    }

    public List<JpaPost> getPosts() {
        return posts;
    }
}
