package it.cosenonjaviste.core.post;

import java.util.List;

import it.cosenonjaviste.core.utils.ObservableParcelerArrayList;
import it.cosenonjaviste.lib.mvp.ListModelAdapter;
import it.cosenonjaviste.model.Author;
import it.cosenonjaviste.model.Category;
import it.cosenonjaviste.model.Post;

public class PostListModel extends ListModelAdapter<Post> {

    ObservableParcelerArrayList<Post> items = new ObservableParcelerArrayList<>();

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

    @Override public ObservableParcelerArrayList<Post> getItems() {
        return items;
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
