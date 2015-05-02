package it.cosenonjaviste.author;

import java.util.List;

import it.cosenonjaviste.model.Author;

public class AuthorListModel {
    List<Author> items;

    boolean errorLoading;

    public boolean isEmpty() {
        return items == null || items.isEmpty();
    }

    public int size() {
        return items.size();
    }

    public Author get(int index) {
        return items.get(index);
    }

    public void done(List<Author> items) {
        this.items = items;
        errorLoading = false;
    }

    public void error() {
        items = null;
        errorLoading = true;
    }

    public List<Author> getItems() {
        return items;
    }

    public boolean isError() {
        return errorLoading;
    }
}
