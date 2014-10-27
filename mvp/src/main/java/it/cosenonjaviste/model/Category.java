package it.cosenonjaviste.model;

import com.google.gson.annotations.SerializedName;

public class Category {
    long id;

    String title;

    @SerializedName("post_count")
    int postCount;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPostCount() {
        return postCount;
    }
}
