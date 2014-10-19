package it.cosenonjaviste.testableandroidapps.model;

import java.util.Date;

public class Post {
    private long id;
    private Author author;
    private String title;
    private Date date;
    private String url;
    private String excerpt;

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
