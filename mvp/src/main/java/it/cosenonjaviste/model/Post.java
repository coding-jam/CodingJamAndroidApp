package it.cosenonjaviste.model;

import java.util.Date;

public class Post {
    long id;
    Author author;
    String title;
    Date date;
    String url;
    String excerpt;

    public Post() {
    }

    public Post(long id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public Author getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public String getExcerpt() {
        return excerpt;
    }
}
