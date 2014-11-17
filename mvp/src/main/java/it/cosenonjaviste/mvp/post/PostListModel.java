package it.cosenonjaviste.mvp.post;

import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.Post;
import it.cosenonjaviste.mvp.base.optional.OptionalList;

public class PostListModel {

    OptionalList items = new OptionalList<>();

    Category category;

    boolean moreDataAvailable;

    Author author;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isMoreDataAvailable() {
        return moreDataAvailable;
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        this.moreDataAvailable = moreDataAvailable;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public OptionalList<Post> getItems() {
        return items;
    }
}
