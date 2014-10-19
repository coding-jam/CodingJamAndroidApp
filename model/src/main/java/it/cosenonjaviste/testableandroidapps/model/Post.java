package it.cosenonjaviste.testableandroidapps.model;

public class Post {
    private long id;
    private Author author;
    private String title;
    private String date;
    private String url;

    public long getId() {
        return id;
    }

    public Author getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

}
