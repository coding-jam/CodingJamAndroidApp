package it.cosenonjaviste.model;

import com.google.gson.annotations.SerializedName;

public class Category {
    long id;

    String title;

    @SerializedName("post_count")
    int postCount;

    public Category() {
    }

    public Category(long id, String title, int postCount) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (id != category.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
