package com.guyburton.fitclub.persistence;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class LoadedPage {

    @Id
    private String url;

    @Lob
    private String content;

    public LoadedPage() {

    }

    public LoadedPage(String url, String content) {
        this.url = url;
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

