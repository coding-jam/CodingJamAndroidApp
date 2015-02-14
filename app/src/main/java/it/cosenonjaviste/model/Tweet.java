package it.cosenonjaviste.model;

import org.parceler.Parcel;

import java.util.Date;

@Parcel
public class Tweet {
    long id;
    String text;
    Date createdAt;
    String userImage;
    String author;

    public Tweet() {
    }

    public Tweet(long id, String text, Date createdAt, String userImage, String author) {
        this();
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.userImage = userImage;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getUserImage() {
        return userImage;
    }

    public String getAuthor() {
        return author;
    }
}
