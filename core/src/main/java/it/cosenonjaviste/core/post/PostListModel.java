package it.cosenonjaviste.core.post;

import java.util.List;

import it.cosenonjaviste.core.model.Author;
import it.cosenonjaviste.core.model.Category;
import it.cosenonjaviste.core.model.Post;
import it.cosenonjaviste.lib.mvp.ListModelAdapter;

public class PostListModel extends ListModelAdapter<Post> {

    List<Post> items;

    Category category;

    boolean moreDataAvailable;

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

    @Override public void setItems(List<Post> items) {
        this.items = items;
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
