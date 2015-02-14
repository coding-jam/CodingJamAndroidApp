package it.cosenonjaviste.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Category {
    long id;

    String title;

    @SerializedName("post_count")
    int postCount;

    public Category() {
    }

    public Category(long id, String title, int postCount) {
        this();
        this.id = id;
        this.title = title;
        this.postCount = postCount;
    }

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
