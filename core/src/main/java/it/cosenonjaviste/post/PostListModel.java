package it.cosenonjaviste.post;

import java.util.List;

import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.Post;

public class PostListModel {

    List<Post> items;

    Category category;

    boolean moreDataAvailable;

    boolean errorLoading;

    Author author;

    public PostListModel() {
    }

    public PostListModel(Category category) {
        this.category = category;
    }

    public PostListModel(Author author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
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

    public List<Post> getItems() {
        return items;
    }

    public void done(List<Post> items) {
        this.items = items;
        errorLoading = false;
    }

    public void error() {
        items = null;
        errorLoading = true;
    }

    public boolean isError() {
        return errorLoading;
    }

    public void append(List<Post> object) {
        items.addAll(object);
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items == null || items.isEmpty();
    }
}
