package com.guyburton.fitclub.parsing;

import java.time.LocalDate;
import java.util.List;

public class ParsedPage {

    private final LocalDate postDate;

    private final String postedBy;

    private final Integer weekId;

    private final List<ParsedPost> posts;

    private final String url;

    public ParsedPage(LocalDate postDate, String postedBy, Integer weekId, List<ParsedPost> posts, String url) {
        this.postDate = postDate;
        this.postedBy = postedBy;
        this.weekId = weekId;
        this.posts = posts;
        this.url = url;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public Integer getWeekId() {
        return weekId;
    }

    public List<ParsedPost> getPosts() {
        return posts;
    }

    @Override
    public String toString() {
        return "ParsedPage{" +
            "weekId=" + weekId +
            ", postedBy='" + postedBy + '\'' +
            ", postDate=" + postDate +
            '}';
    }

    public String getUrl() {
        return url;
    }
}
